package ao.com.wundu.notification.entity;

import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
@Table(name = "notification")
public class notification {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String user_id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private LocalDate create_at;

    private Boolean is_read; 
}
