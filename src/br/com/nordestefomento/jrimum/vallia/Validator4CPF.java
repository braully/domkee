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
 * Created at: 30/03/2008 - 18:21:21
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
 * Criado em: 30/03/2008 - 18:21:21
 * 
 */


package br.com.nordestefomento.jrimum.vallia;


/**
 * 
 * O cadastro de pessoa física tem as seguintes características:
 * <ul>
 * <li>Contém apenas números.</li>
 * <li>Possui tamanho 11 sem formatação e 14 com formatação.</li>
 * <li>Pode estar no formatador ###.###.###-XX, onde XX é o dígito verificador.</li>
 * </ul>
 * 
 * A validação consiste em verificar essas características e se o dígito verificador é válido.
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
class Validator4CPF extends AValidator4CPRF {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7284156043760247784L;

	/**
	 * @see br.com.nordestefomento.jrimum.vallia.AValidator4CPRF#isValido()
	 */
	@Override
	public boolean isValido() {
		
		boolean isValido = false;
		int dv = 0;
		int dvCalculado = -1;
		
		dv = Integer.parseInt(getCodigoDoCadastro().substring(9, 11));

		dvCalculado = digitoVerificador.calcule(getCodigoDoCadastro().substring(0, 9));
		
		isValido = (dvCalculado >= 0 && dv == dvCalculado);
		
		return isValido;
	}

	/**
	 * @see br.com.nordestefomento.jrimum.vallia.AValidator4CPRF#removeFormatacao()
	 */
	@Override
	protected void removeFormatacao() {
		
		String codigo = getCodigoDoCadastro();
		
		codigo = codigo.replace(".", "");
		codigo = codigo.replace("-", "");

		codigoDoCadastro.delete(0, codigoDoCadastro.length());
		
		codigoDoCadastro.append(codigo);
	}
	
}
