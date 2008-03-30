/*
 * Copyright 2008 JRimum Project
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * 
 * Created at: 30/03/2008 - 18:51:09
 * 
 * ================================================================================
 * 
 * Direitos autorais 2008 JRimum Project
 * 
 * Licenciado sob a Licença Apache, Versão 2.0 ("LICENÇA"); você não pode usar
 * esse arquivo exceto em conformidade com a esta LICENÇA. Você pode obter uma
 * cópia desta LICENÇA em http://www.apache.org/licenses/LICENSE-2.0 A menos que
 * haja exigência legal ou acordo por escrito, a distribuição de software sob
 * esta LICENÇA se dará “COMO ESTÁ”, SEM GARANTIAS OU CONDIÇÕES DE QUALQUER
 * TIPO, sejam expressas ou tácitas. Veja a LICENÇA para a redação específica a
 * reger permissões e limitações sob esta LICENÇA.
 * 
 * Criado em: 30/03/2008 - 18:51:09
 * 
 */


package br.com.nordestefomento.jrimum.vallia.digitoverificador;

import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import br.com.nordestefomento.jrimum.utilix.Filler;
import br.com.nordestefomento.jrimum.utilix.Util4String;


/**
 * 
 * O cálculo do dígito verificador do CPF é realizado em duas etapas e é
 * auxiliado pela rotina de módulo 11. <br />
 * Abaixo é demonstrado como esse cálculo é feito:
 * 
 * <h3>Exemplo para um número hipotético 222.333.666-XX:</h3>
 * <p>
 * Primeiramente obtém-se um número R, calculado através da rotina de módulo 11,
 * a partir dos nove primeiros números do CPF, nesse caso 222333666. <br />
 * Para obter o primeiro dígito verificador deve-se seguir a seguinte lógica:
 * <br />
 * <br />
 * Se o número R for menor que 2, o dígito terá valor 0 (zero); senão, será a
 * subtração do valor do módulo (11) menos o valor do número R, ou seja,
 * <code>DV = 11 - R</code>.
 * </p>
 * <p>
 * Para obter o segundo dígito verificador é da mesma forma do primeiro, porém
 * deve ser calculado a partir dos dez primeiros números do CPF, ou seja,
 * 222333666 + primeiro dígito.
 * </p>
 * <p>
 * Obs.: Os limites mínimos e máximos do módulo 11 para o cálculo do primeiro e
 * do segundo dígito verificador são 2 - 10 e 2 e 11, respectivamente.
 * </p>
 * 
 * @see Modulo11
 * 
 * @author Gabriel Guimarães
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 * @author Misael Barreto 
 * @author Rômulo Augusto
 * @author <a href="http://www.nordeste-fomento.com.br">Nordeste Fomento Mercantil</a>
 * 
 * @since JMatryx 1.0
 * 
 * @version 1.0
 */
public class DV4CPF extends ADigitoVerificador {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2059692008894172695L;

	/**
	 * Expressão regular para validação dos nove primeiros números do CPF sem
	 * formatação: "#########".
	 */
	private static final String REGEX_CPF_DV = "\\d{9}";

	/**
	 * Expressão regular para validação dos nove primeiros números do CPF
	 * formatado: "###.###.###".
	 */
	private static final String REGEX_CPF_DV_FORMATED = "\\d{3}\\.\\d{3}\\.\\d{3}";

	/**
	 * @see br.com.nordestefomento.jrimum.vallia.digitoverificador.ADigitoVerificador#calcular(long)
	 */
	@Override
	public int calcular(long numero) {

		return calcular(Util4String.complete_x(numero, Filler.LONG_ZERO_LEFT, 9));
	}

	/**
	 * @see br.com.nordestefomento.jrimum.vallia.digitoverificador.ADigitoVerificador#calcular(java.lang.String)
	 */
	@Override
	public int calcular(String numero) throws IllegalArgumentException {

		int dv1 = 0;
		int dv2 = 0;
		boolean isFormatoValido = false;

		validacao: {

			if (StringUtils.isNotBlank(numero)) {

				isFormatoValido = (Pattern.matches(REGEX_CPF_DV, numero) || Pattern
						.matches(REGEX_CPF_DV_FORMATED, numero));

				if (isFormatoValido) {

					numero = StringUtils.replaceChars(numero, ".", "");

					dv1 = calcular(numero, 10);
					dv2 = calcular(numero + dv1, 11);

					break validacao;
				}
			}

			throw new IllegalArgumentException(
					"O CPF [ "+numero+" ] deve conter apenas números, sendo eles no formatador ###.###.### ou ######### !");

		}

		return Integer.parseInt(dv1 + "" + dv2);

	}

	/**
	 * Método auxiliar para o cálculo do dígito verificador. <br />
	 * Calcula os dígitos separadamente.
	 * 
	 * @param numero -
	 *            número a partir do qual será extraído o dígito verificador.
	 * @param limiteMaximoDoModulo -
	 *            limite máximo do módulo utilizado, no caso, módulo 11.
	 * @return um número que faz parte de um dígito verificador.
	 * @throws IllegalArgumentException
	 *             caso o número não esteja no formatador desejável.
	 */
	private int calcular(String numero, int limiteMaximoDoModulo)
			throws IllegalArgumentException {

		int dv = 0;
		int resto = 0;
		AModulo modulo = AModulo.getInstance(EnumModulo.MODULO_11);

		modulo.setLimiteMaximo(limiteMaximoDoModulo);
		resto = modulo.calcular(numero);

		if (resto >= 2) {

			dv = modulo.getValor() - resto;
		}

		return dv;
	}
}
