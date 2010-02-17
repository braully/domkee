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
 * Created at: 30/03/2008 - 18:59:18
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
 * Criado em: 30/03/2008 - 18:59:18
 * 
 */

package br.com.nordestefomento.jrimum.domkee.financeiro.banco;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import br.com.nordestefomento.jrimum.utilix.ObjectUtil;

/**
 * <p>
 * Um dado bancário qualquer para condições específicas de determinados bancos
 * ou implementações de campos livre.
 * </p>
 * 
 * <p>
 * Mais dados bancários podem ser necessários a um título para gerar um boleto,
 * por exemplo. Assim, dependendo do banco, talvez seja necessário informar mais
 * dados além de:
 * </p>
 * 
 * <ul>
 * <li>Valor do título;</li>
 * <li>Vencimento;</li>
 * <li>Nosso número;</li>
 * <li>Código do banco;</li>
 * <li>Data de vencimento;</li>
 * <li>Agência/Código do cedente</li>;
 * <li>Código da carteira;</li>
 * <li>Código da moeda;</li>
 * </ul>
 * 
 * <p>
 * Definidos como padrão pela FEBRABAN.
 * </p>
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 * 
 * @version 0.2
 * @since 0.2
 */
public final class ParametrosBancarios {

	private Map<String, Object> dadosMap;

	public ParametrosBancarios() {
		getInstance();
	}

	public ParametrosBancarios(String nome, Object valor) {

		add(nome, valor);
	}

	public boolean contemComNome(String nome) {

		isNomeValido(nome);

		return dadosMap.containsKey(nome);
	}

	public boolean contemComValor(Object valor) {

		isValorValido(valor);

		return dadosMap.containsValue(valor);
	}

	@SuppressWarnings("unchecked")
	public <V> V getValor(String nome) {

		isNomeValido(nome);

		return (V) dadosMap.get(nome);
	}

	public boolean isVazio() {

		return dadosMap.isEmpty();
	}

	public Set<String> nomes() {

		return dadosMap.keySet();
	}

	public Collection<?> valores() {

		return dadosMap.values();
	}

	public Set<java.util.Map.Entry<String, Object>> entradas() {

		return dadosMap.entrySet();
	}

	public ParametrosBancarios add(String nome, Object valor) {

		isNomeValido(nome);
		isValorValido(valor);

		getInstance();

		dadosMap.put(nome, valor);

		return this;
	}

	public ParametrosBancarios addTodos(ParametrosBancarios dados) {

		ObjectUtil.isNotNull(dados, "dados");

		this.dadosMap.putAll(dados.dadosMap);

		return this;
	}

	@SuppressWarnings("unchecked")
	public <V> V remover(String nome) {

		isNomeValido(nome);

		return (V) dadosMap.remove(nome);
	}

	public void limpar() {

		dadosMap.clear();
	}

	public int quantidade() {

		return dadosMap.size();
	}

	private void getInstance() {

		if (dadosMap == null) {
			dadosMap = new HashMap<String, Object>();
		}
	}

	private boolean isNomeValido(String nome) {

		return ObjectUtil.isNotNull(nome, "nome");
	}

	private boolean isValorValido(Object valor) {

		return ObjectUtil.isNotNull(valor, "valor");
	}

}