package org.example.datn.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Voucher")
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String ma;

    @Temporal(TemporalType.DATE)
    private Date ngayBatDau;

    @Temporal(TemporalType.DATE)
    private Date ngayKetThuc;

    private Integer phanTramGiam;

    private Boolean trangThai;

    @OneToMany(mappedBy = "voucher")
    @JsonIgnore 
    private List<HoaDon> hoaDons;
}
