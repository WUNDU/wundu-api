package ao.com.wundu.infra.presentation.entities;
import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private Double amount;

    private String description;

    private String type;

    private String User_id;
    
    private LocalDateTime dateTime;

    public Transaction() {}

    public Transaction(String id, Double amount, String description, String type, LocalDateTime dateTime, String User_id) {
        this.id = id;
        this.amount = amount;
        this.description = description;
        this.type = type;
        this.dateTime = dateTime;
        this.User_id = User_id;
    }

    public Transaction(Double amount, String description, String type, String User_id) {
        this.amount = amount;
        this.description = description;
        this.type = type;
        this.User_id = User_id;
        this.dateTime = LocalDateTime.now();
    }

    public String getId() { return id; }
    public Double getAmount() { return amount; }
    public String getDescription() { return description; }
    public String getType() { return type; }
    public String getUser_id() { return User_id; }
    public LocalDateTime getDateTime() { return dateTime; }

    public void setAmount(Double amount) { this.amount = amount; }
    public void setDescription(String description) { this.description = description; }
    public void setType(String type) { this.type = type; }
}