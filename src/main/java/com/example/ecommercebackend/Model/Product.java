package Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int product_id;

    @Column(nullable = false)
    private String product_name;

    @Column(nullable = false)
    private double product_price;

    @Column(nullable = false)
    private boolean stock;

    @Column(nullable = false)
    private boolean live;

    @Column(nullable = false)
    private String product_imageName;

    @Column(nullable = false)
    private String product_description;

    @Override
    public String toString() {
        return "Product{" +
                "product_id=" + product_id +
                ", product_name='" + product_name + '\'' +
                ", product_price=" + product_price +
                ", stock=" + stock +
                ", live=" + live +
                ", product_imageName='" + product_imageName + '\'' +
                ", product_description='" + product_description + '\'' +
                '}';
    }
}
