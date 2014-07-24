package br.com.caelum.agiletickets.models;

import static org.junit.Assert.*;

import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.junit.Test;

public class EspetaculoTest {

	@Test
	public void deveInformarSeEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertTrue(ivete.hasVagas(5));
	}

	@Test
	public void deveInformarSeEhPossivelReservarAQuantidadeExataDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertTrue(ivete.hasVagas(6));
	}

	@Test
	public void DeveInformarSeNaoEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertFalse(ivete.hasVagas(15));
	}

	@Test
	public void DeveInformarSeEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(4));

		assertTrue(ivete.hasVagas(5, 3));
	}

	@Test
	public void DeveInformarSeEhPossivelReservarAQuantidadeExataDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(4));

		assertTrue(ivete.hasVagas(10, 3));
	}

	@Test
	public void DeveInformarSeNaoEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(2));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertFalse(ivete.hasVagas(5, 3));
	}

	private Sessao sessaoComIngressosSobrando(int quantidade) {
		Sessao sessao = new Sessao();
		sessao.setTotalIngressos(quantidade * 2);
		sessao.setIngressosReservados(quantidade);

		return sessao;
	}

	@Test
	public void deveMontar1SessaoParaEspetaculoComRepeticaoDiariaEDataInicialIgualADataFinal() {
		Espetaculo showDoMilhao = new Espetaculo();

		LocalDate dataInicial = new LocalDate(2010, 10, 10);
		LocalDate dataFinal = new LocalDate(2010, 10, 10);
		LocalTime horario = new LocalTime(22, 00);
		Periodicidade periodicidade = Periodicidade.DIARIA;

		List<Sessao> sessoesDoEspetaculo = showDoMilhao.criaSessoes(
				dataInicial, dataFinal, horario, periodicidade);
		assertEquals(dataInicial.toDateTime(horario), sessoesDoEspetaculo.get(0).getInicio());
	}

	@Test
	public void deveMontarNSessoesParaEspetaculoComRepeticaoDiariaEDataFinalMaiorQueDataInicial() {
		Espetaculo showDoMilhao = new Espetaculo();

		LocalDate dataInicial = new LocalDate(2010, 10, 10);
		LocalDate dataFinal = new LocalDate(2010, 10, 11);
		LocalTime horario = new LocalTime(22, 00);
		Periodicidade periodicidade = Periodicidade.DIARIA;

		List<Sessao> sessoesDoEspetaculo = showDoMilhao.criaSessoes(
				dataInicial, dataFinal, horario, periodicidade);
		assertEquals(dataInicial.toDateTime(horario), sessoesDoEspetaculo.get(0).getInicio());
		assertEquals(dataFinal.toDateTime(horario), sessoesDoEspetaculo.get(1).getInicio());
	}

	@Test
	public void deveMontar1SessaoParaEspetaculoComRepeticaoSemanalEMenosDe7DiasDeDiferencaEntreDataInicialEFinal() {
		Espetaculo showDoMilhao = new Espetaculo();

		LocalDate dataInicial = new LocalDate(2010, 10, 10);
		LocalDate dataFinal = new LocalDate(2010, 10, 10);
		LocalTime horario = new LocalTime(22, 00);
		Periodicidade periodicidade = Periodicidade.SEMANAL;

		List<Sessao> sessoesDoEspetaculo = showDoMilhao.criaSessoes(
				dataInicial, dataFinal, horario, periodicidade);
		assertEquals(dataInicial.toDateTime(horario), sessoesDoEspetaculo.get(0).getInicio());
	}

	@Test
	public void deveMontarNSessoesParaEspetaculoComRepeticaoSemanalEMaisDe7DiasDeDiferencaEntreDataInicialEFinal() {
		Espetaculo showDoMilhao = new Espetaculo();

		LocalDate dataInicial = new LocalDate(2010, 10, 10);
		LocalDate dataFinal = new LocalDate(2010, 10, 18);
		LocalTime horario = new LocalTime(22, 00);
		Periodicidade periodicidade = Periodicidade.SEMANAL;

		List<Sessao> sessoesDoEspetaculo = showDoMilhao.criaSessoes(
				dataInicial, dataFinal, horario, periodicidade);
		assertEquals(dataInicial.toDateTime(horario), sessoesDoEspetaculo.get(0).getInicio());
		assertEquals(dataInicial.plusDays(7).toDateTime(horario), sessoesDoEspetaculo.get(1).getInicio());
	}

}
