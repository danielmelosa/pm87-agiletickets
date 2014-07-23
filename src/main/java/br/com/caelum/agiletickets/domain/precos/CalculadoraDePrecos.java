package br.com.caelum.agiletickets.domain.precos;

import java.math.BigDecimal;

import br.com.caelum.agiletickets.models.Sessao;

public class CalculadoraDePrecos {

	private static final BigDecimal DEZ_PORCENTO = new BigDecimal("0.10");

	public static BigDecimal calcula(Sessao sessao, Integer quantidade) {
		BigDecimal preco;

		if (sessao.isTipoDeSessaoCinema() || sessao.isTipoDeSessaoShow()) {
			preco = calcularPrecoParaCinemaOuShow(sessao);
		} else if (sessao.isTipoDeSessaoEspetaculo() || sessao.isTipoDeSessaoOrquestra()) {
			preco = calcularPrecoParaEspetaculoOuOrquestra(sessao);
		} else {
			preco = sessao.getPreco();
		}

		return preco.multiply(BigDecimal.valueOf(quantidade));
	}

	private static BigDecimal calcularPrecoParaEspetaculoOuOrquestra(Sessao sessao) {
		BigDecimal preco;
		if (sessao.has50PorcentoDeIngressosDisponiveisParaVenda()) {
			preco = sessao.getPrecoCom20PorcentoDeAumento();
		} else {
			preco = sessao.getPreco();
		}

		if (sessao.getDuracaoEmMinutos() > 60) {
			preco = preco.add(sessao.getPreco().multiply(DEZ_PORCENTO));
		}
		return preco;
	}

	private static BigDecimal calcularPrecoParaCinemaOuShow(Sessao sessao) {
		BigDecimal preco;
		// quando estiver acabando os ingressos...
		if (sessao.has5PorcentoDeIngressosDisponiveisParaVenda()) {
			preco = sessao.getPrecoCom10PorcentoDeAumento();
		} else {
			preco = sessao.getPreco();
		}
		return preco;
	}

}