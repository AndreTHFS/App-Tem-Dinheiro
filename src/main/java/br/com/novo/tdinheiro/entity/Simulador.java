package br.com.novo.tdinheiro.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "TB_SIMULADOR")
public class Simulador {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID simuladorId;

    @NotNull
    private String nomeLead;
    @NotNull
    private String foneLead;
    @Enumerated(EnumType.STRING)
    @Column(name = "orgaos")

    private Orgao orgao;
    private Double parcela;
    @NotNull
    private Double valor;

    @CreationTimestamp
    private Instant createTime;

    public UUID getSimuladorId() {
        return simuladorId;
    }

    public void setSimuladorId(UUID simuladorId) {
        this.simuladorId = simuladorId;
    }

    public String getNomeLead() {
        return nomeLead;
    }

    public void setNomeLead(String nomeLead) {
        this.nomeLead = nomeLead;
    }

    public String getFoneLead() {
        return foneLead;
    }

    public void setFoneLead(String foneLead) {
        this.foneLead = foneLead;
    }

    public Orgao getOrgao() {
        return orgao;
    }

    public void setOrgao(Orgao orgao) {
        this.orgao = orgao;
    }

    public Double getParcela() {
        return parcela;
    }

    public void setParcela(Double parcela) {
        this.parcela = parcela;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }
}
