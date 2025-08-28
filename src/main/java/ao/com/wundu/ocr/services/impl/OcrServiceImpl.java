package ao.com.wundu.ocr.service.impl;

import ao.com.wundu.exception.ResourceNotFoundException;
import ao.com.wundu.ocr.dto.OcrResponse;
import ao.com.wundu.ocr.entity.OcrRecord;
import ao.com.wundu.ocr.repository.OcrRepository;
import ao.com.wundu.ocr.service.OcrService;
import ao.com.wundu.upload.service.UploadService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class OcrServiceImpl implements OcrService {

    private final RestTemplate restTemplate;
    private final OcrRepository ocrRepository;
    private final UploadService uploadService;
    private final ObjectMapper mapper = new ObjectMapper();

    @Value("${huggingface.api.token}")
    private String hfToken;

    @Value("${huggingface.model.id:microsoft/trocr-large-printed}")
    private String modelId;

    public OcrServiceImpl(RestTemplate restTemplate, OcrRepository ocrRepository, UploadService uploadService) {
        this.restTemplate = restTemplate;
        this.ocrRepository = ocrRepository;
        this.uploadService = uploadService;
    }

    @Override
    public OcrResponse processOcr(MultipartFile file) {
        byte[] bytes = uploadService.uploadOcr(file);
        String contentType = file.getContentType() == null ? "" : file.getContentType().toLowerCase();
        String originalName = file.getOriginalFilename() == null ? "file" : file.getOriginalFilename();
        List<String> pages = new ArrayList<>();
        try {
            if ("application/pdf".equalsIgnoreCase(contentType) || originalName.toLowerCase().endsWith(".pdf")) {
                try (PDDocument doc = PDDocument.load(bytes)) {
                    PDFRenderer renderer = new PDFRenderer(doc);
                    int pagesCount = doc.getNumberOfPages();
                    for (int i = 0; i < pagesCount; i++) {
                        BufferedImage image = renderer.renderImageWithDPI(i, 300);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        ImageIO.write(image, "png", baos);
                        baos.flush();
                        byte[] imgBytes = baos.toByteArray();
                        baos.close();
                        String pageText = callHuggingFace(imgBytes);
                        pages.add(pageText);
                    }
                }
            } else if (contentType.startsWith("image/") || originalName.matches("(?i).*\\.(png|jpe?g)$")) {
                String text = callHuggingFace(bytes);
                pages.add(text);
            }
            String fullText = pages.stream().filter(Objects::nonNull).collect(Collectors.joining("\n\n"));
            OcrRecord record = new OcrRecord(originalName, contentType, fullText, file.getSize());
            OcrRecord saved = ocrRepository.save(record);
            return new OcrResponse(saved.getId(), saved.getFileName(), saved.getContentType(), saved.getFileSize(), saved.getExtractedText());
        } catch (IOException e) {
            throw new ResourceNotFoundException("Erro ao processar arquivo para OCR", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String callHuggingFace(byte[] payload) {
        String url = "https://api-inference.huggingface.co/models/" + modelId;
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(hfToken);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        HttpEntity<byte[]> request = new HttpEntity<>(payload, headers);
        ResponseEntity<String> resp = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
        if (!resp.getStatusCode().is2xxSuccessful()) {
            throw new ResourceNotFoundException("Erro no serviço OCR: status " + resp.getStatusCodeValue(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        String body = resp.getBody();
        if (body == null) return "";
        try {
            JsonNode node = mapper.readTree(body);
            if (node.isArray()) {
                StringBuilder sb = new StringBuilder();
                for (JsonNode n : node) {
                    if (n.has("generated_text")) sb.append(n.get("generated_text").asText());
                    else if (n.has("text")) sb.append(n.get("text").asText());
                    else sb.append(n.toString());
                }
                return sb.toString().trim();
            } else if (node.has("generated_text")) {
                return node.get("generated_text").asText().trim();
            } else if (node.has("text")) {
                return node.get("text").asText().trim();
            } else {
                return body.trim();
            }
        } catch (IOException e) {
            return body.trim();
        }
    }
}
