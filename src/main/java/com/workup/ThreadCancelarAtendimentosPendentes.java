
package com.workup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class ThreadCancelarAtendimentosPendentes implements Runnable {

	@Override
	public void run() {

		for (;;) {
			try {
				Thread.sleep(30000);
				
				Document doc = Jsoup.connect("http://localhost:9904/cancelaratendimentospendentes").get();
				
				//System.out.println("Api: "+doc.getAllElements().toString());
				System.out.println("Api de cancelamento de atendimentos chamada com sucesso!");
			} catch (Exception e) {
				System.out.println("Erro ao criar URL. Formato inválido.");
			}
		}
	}

}
