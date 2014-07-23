package br.com.caelum.agiletickets.models;

import java.math.BigDecimal;
import java.util.Locale;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

@Entity
public class Sessao {

	public static final double INDICADOR_CINCO_PORCENTO_INGRESSOS = 0.05;
	public static final double INDICADOR_DEZ_PORCENTO_INGRESSOS = 0.10;
	public static final double INDICADOR_VINTE_PORCENTO_INGRESSOS = 0.20;
	public static final double INDICADOR_CINQUENTA_PORCENTO_INGRESSOS = 0.50;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Espetaculo espetaculo;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime inicio;

	private Integer duracaoEmMinutos;

	private Integer totalIngressos = 0;

	private Integer ingressosReservados = 0;

	private BigDecimal preco;

	public Long getId() {
		return id;
	}

	public void setEspetaculo(Espetaculo espetaculo) {
		this.espetaculo = espetaculo;
	}

	public DateTime getInicio() {
		return inicio;
	}

	public void setInicio(DateTime inicio) {
		this.inicio = inicio;
	}

	public Espetaculo getEspetaculo() {
		return espetaculo;
	}

	public Integer getDuracaoEmMinutos() {
		return duracaoEmMinutos;
	}

	public void setDuracaoEmMinutos(Integer duracaoEmMinutos) {
		this.duracaoEmMinutos = duracaoEmMinutos;
	}

	public String getDia() {
		return inicio.toString(DateTimeFormat.shortDate().withLocale(
				new Locale("pt", "BR")));
	}

	public String getHora() {
		return inicio.toString(DateTimeFormat.shortTime().withLocale(
				new Locale("pt", "BR")));
	}

	public Integer getTotalIngressos() {
		return totalIngressos;
	}

	public void setTotalIngressos(Integer totalIngressos) {
		this.totalIngressos = totalIngressos;
	}

	public Integer getIngressosReservados() {
		return ingressosReservados;
	}

	public void setIngressosReservados(Integer ingressosReservados) {
		this.ingressosReservados = ingressosReservados;
	}

	public Integer getIngressosDisponiveis() {
		// faz a conta de total de ingressos menos ingressos reservados
		return totalIngressos - ingressosReservados;
	}

	public void reserva(Integer numeroDeIngressos) {
		// soma quantidade na variavel ingressos reservados
		this.ingressosReservados += numeroDeIngressos;
	}

	public boolean podeReservar(Integer numeroDeIngressos) {
		int sobraram = getIngressosDisponiveis() - numeroDeIngressos;
		boolean naoTemEspaco = sobraram < 0;

		return !naoTemEspaco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public BigDecimal getPrecoCom20PorcentoDeAumento() {
		return getPreco().add(
				getPreco().multiply(BigDecimal.valueOf(INDICADOR_VINTE_PORCENTO_INGRESSOS)));
	}
	
	public BigDecimal getPrecoCom10PorcentoDeAumento() {
		return getPreco().add(
				getPreco().multiply(BigDecimal.valueOf(INDICADOR_DEZ_PORCENTO_INGRESSOS)));
	}

	public boolean has5PorcentoDeIngressosDisponiveisParaVenda() {
		return getIngressosDisponiveis()
				/ this.getTotalIngressos().doubleValue() <= INDICADOR_CINCO_PORCENTO_INGRESSOS;
	}

	public boolean has50PorcentoDeIngressosDisponiveisParaVenda() {
		return getIngressosDisponiveis() / getTotalIngressos().doubleValue() <= INDICADOR_CINQUENTA_PORCENTO_INGRESSOS;
	}

	public boolean isTipoDeSessaoCinema() {
		return this.getEspetaculo().getTipo().equals(TipoDeEspetaculo.CINEMA);
	}

	public boolean isTipoDeSessaoShow() {
		return this.getEspetaculo().getTipo().equals(TipoDeEspetaculo.SHOW);
	}

	public boolean isTipoDeSessaoEspetaculo() {
		return this.getEspetaculo().getTipo().equals(TipoDeEspetaculo.BALLET);
	}

	public boolean isTipoDeSessaoOrquestra() {
		return this.getEspetaculo().getTipo()
				.equals(TipoDeEspetaculo.ORQUESTRA);
	}


}
