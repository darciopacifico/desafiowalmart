package br.com.walmart.desafio;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.BeanComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.Assert;
import org.testng.annotations.Test;

import br.com.walmart.Application;
import br.com.walmart.controller.ShortestpathController;
import br.com.walmart.exception.WalmartException;
import br.com.walmart.exception.WalmartRuntimeException;
import br.com.walmart.vo.CaminhoMalha;
import br.com.walmart.vo.MalhaViaria;
import br.com.walmart.vo.PathCost;

@SpringApplicationConfiguration(classes = Application.class)
@ContextConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class DesafioWalmartApplicationTests extends AbstractTestNGSpringContextTests {

	protected static final String MALHA_ALPHAVILLE = "Alphaville";

	@Autowired
	ShortestpathController shortestpathController;

	@Test
	public void testResetaMalha() {

		shortestpathController.deleteMalha(MALHA_ALPHAVILLE);

	}

	@Test(dependsOnMethods = "testResetaMalha")
	public void testCriaMalha() throws WalmartException {

		MalhaViaria malhaViaria = createMalhaModelo();

		shortestpathController.criaMalhaViaria(malhaViaria);

	}


	@Test(dependsOnMethods = "testCriaMalha")
	public void testCriaMalhaDeNovoErro() {

		MalhaViaria malhaViaria = createMalhaModelo();

		
		try {
			shortestpathController.criaMalhaViaria(malhaViaria);
			
			Assert.fail("Era pra lancar excecao ao tentar criar uma malha com o mesmo nome 2 vezes!");
			
		} catch (WalmartException e) {
			
			Assert.assertEquals(e.getClass(), WalmartException.class);
			
		}

		
		
		
		
	}

	
	
	@Test(dependsOnMethods = "testCriaMalha")
	public void testConsultaMalha() {

		MalhaViaria malhaModelo = createMalhaModelo();

		MalhaViaria malhaRetorno = shortestpathController.getMalha(MALHA_ALPHAVILLE);

		Assert.assertNotNull(malhaRetorno, "Nao foi possivel encontra malha! Ela foi criada previamente?");

		Assert.assertEquals(malhaModelo, malhaRetorno, "Os nomes das malhas nao sao iguais!");

		List<CaminhoMalha> caminhoModelo = malhaModelo.getCaminhos();
		List<CaminhoMalha> caminhoRetorno = malhaRetorno.getCaminhos();

		// ordena os vertices de caminho por inicio, fim e distancia, apenas para comparar se sao os mesmos.
		ordenaCaminho(caminhoModelo);
		ordenaCaminho(caminhoRetorno);

		Assert.assertEquals(caminhoModelo, caminhoRetorno, "Os caminhos das malhas nao sao os mesmos do caminho model! Houve algum erro na gravacao/recuperacao dos dados!");

	}

	protected void ordenaCaminho(List<CaminhoMalha> caminhos) {

		Comparator<? super CaminhoMalha> comparatorD = new BeanComparator<CaminhoMalha>("distance");
		Comparator<? super CaminhoMalha> comparatorE = new BeanComparator<CaminhoMalha>("endLocation");
		Comparator<? super CaminhoMalha> comparatorA = new BeanComparator<CaminhoMalha>("startLocation");

		Collections.sort(caminhos, comparatorD);
		Collections.sort(caminhos, comparatorE);
		Collections.sort(caminhos, comparatorA);

	}

	/**
	 * 
	 * @throws WalmartException
	 */
	@Test(dependsOnMethods = "testCriaMalha")
	public void testMenorCaminho() throws WalmartException {

		PathCost path = shortestpathController.shortestpath(MALHA_ALPHAVILLE, 10f, 2.5f, "A", "D");

		Assert.assertNotNull(path, "O mapa aphaville nao foi registrado previamente ou o caminho nao foi encontrado!");

		Assert.assertEquals(new Double(6.25d), path.getTotalCost(), "O custo esperado do caminho deveria ser 6.25");

	}

	/**
	 * 
	 * @throws WalmartException
	 */
	@Test(dependsOnMethods = "testCriaMalha")
	public void testMenorCaminhoErro() throws WalmartException {

		try {

			PathCost path = shortestpathController.shortestpath(MALHA_ALPHAVILLE, 10f, 2.5f, "A", "X");

			Assert.fail("Deveria ter lançado Exception. O ponto X nao existe no mapa: " + path);

		} catch (Exception e) {

			Assert.assertEquals(e.getClass(), WalmartRuntimeException.class);

		}

	}

	/**
	 * 
	 * @throws WalmartException
	 */
	@Test(dependsOnMethods = "testCriaMalha")
	public void testConsultaNomeMalhas() throws WalmartException {

		Set<String> malhas = shortestpathController.getMalhas();

		boolean encontrou= false;
		
		for (String malha : malhas) {
			
			if(malha==null){
				Assert.fail("Nao era pra existir um nome de malha null!");
			}
			
			
			if(MALHA_ALPHAVILLE.equals(malha)){
				encontrou= true;
			}
			
		}
		

		Assert.assertEquals(encontrou, true,"Nao foi encontrada a malha de nome '"+MALHA_ALPHAVILLE+"'");
		
		
	}

	protected MalhaViaria createMalhaModelo() {
		MalhaViaria malhaViaria = new MalhaViaria();

		malhaViaria.setNomeMapa(MALHA_ALPHAVILLE);

		List<CaminhoMalha> caminhos = malhaViaria.getCaminhos();

		caminhos.add(new CaminhoMalha("A", "B", 10d));
		caminhos.add(new CaminhoMalha("B", "D", 15d));
		caminhos.add(new CaminhoMalha("A", "C", 20d));
		caminhos.add(new CaminhoMalha("C", "D", 30d));
		caminhos.add(new CaminhoMalha("B", "E", 50d));
		caminhos.add(new CaminhoMalha("D", "E", 30d));

		return malhaViaria;
	}

}
