import java.io.*;

//FALTA CORRIGIR TAMANHO DOS CAMPOS PRA FICAR COM UM DISPLAY OK
//FALTA CRIACAO DE UMA LINHA TOTAIS NO FINAL DO RELATORIO
//INCLUIR VARIOS COMPUTADORES

public class Computador {

	char ativo; // n p/ registro excluï¿½do e s pra registro corrente
	String marca; // nome do fabricante / metodo consistirMarca
	String codComp; // codigo do computador / geracao automatica
	String modelo; // descricao do modelo do comp digitacao necessaria
	String processador; // metodo consistirProcessador
	int quantMemoria; // aceitar valores entre 1 e 16
	int tamanhoTela; // metodo consistir tamanho tela
	int quantEstoque; // nao aceitar valor neg
	float preco; // somente valores entre 1000 e 20000
	int quantVendida;
	String dtUltimaVenda; // dd/mm/aaaa
	float total = 0;

	static String vetorMarcas[] = { "Dell", "Lenovo", "HP", "Positivo", "Asus", "Apple", "IBM" };
	static String vetorProcessadores[] = { "Intel Core i3", "Intel Core i5", "Intel Core i7", "Intel Core i9",
			"AMD Ryzen", "AMD Athlon" };
	static int vetorTamanhoTela[] = { 10, 12, 15, 20, 25, 28 };

	public static boolean consistirMarca(String marcaDigitada) {
		boolean valido = false;

		for (int i = 0; i < 7; i++) {
			if (marcaDigitada.equalsIgnoreCase(vetorMarcas[i])) {
				valido = true;
				break;
			} else {
				valido = false;
			}

		}
		if (valido == false) {
			System.out.println("Marca nÃ£o encontrada.");
		}
		return valido;

	}

	public static boolean consistirProcessador(String processadorDigitado) {
		boolean valido = false;

		for (int i = 0; i < 6; i++) {
			if (processadorDigitado.equalsIgnoreCase(vetorProcessadores[i])) {
				valido = true;
				break;
			} else {
				valido = false;
			}
		}
		if (valido == false) {
			System.out.println("Processador nÃ£o encontrado.");
		}
		return valido;

	}

	public static boolean consistirTamanhoTela(int tamanhoTelaDigitada) {
		boolean valido = false;

		for (int i = 0; i < 6; i++) {
			if (tamanhoTelaDigitada == vetorTamanhoTela[i]) {
				valido = true;
				break;
			} else {
				valido = false;
			}
		}
		if (valido == false) {
			System.out.println("Tamanho de tela nÃ£o encontrado.");
		}
		return valido;

	}

	public long pesquisarComputador(String codCompPesq) {
// metodo para localizar um registro no arquivo em disco
		long posicaoCursorArquivo = 0;
		try {
			RandomAccessFile arqComp = new RandomAccessFile("COMP.DAT", "rw");
			while (true) {
				posicaoCursorArquivo = arqComp.getFilePointer(); // posicao do inicio do registro no arquivo
				ativo = arqComp.readChar();
				marca = arqComp.readUTF();
				codComp = arqComp.readUTF();
				modelo = arqComp.readUTF();
				processador = arqComp.readUTF();
				quantMemoria = arqComp.readInt();
				tamanhoTela = arqComp.readInt();
				quantEstoque = arqComp.readInt();
				preco = arqComp.readFloat();
				quantVendida = arqComp.readInt();
				dtUltimaVenda = arqComp.readUTF();

				if (codCompPesq.equals(codComp) && ativo == 'S') {
					arqComp.close();
					return posicaoCursorArquivo;
				}
			}
		} catch (EOFException e) {
			return -1; // registro nao foi encontrado
		} catch (IOException e) {
			System.out.println("Erro na abertura do arquivo  -  programa serÃ¡ finalizado. ");
			System.exit(0);
			return -1;
		}
	}

	public void incluirComputador() {
// metodo para incluir um novo registro no final do arquivo em disco
		try {
			RandomAccessFile arqComp = new RandomAccessFile("COMP.DAT", "rw");
			arqComp.seek(arqComp.length());
			arqComp.writeChar(ativo);
			arqComp.writeUTF(marca);
			arqComp.writeUTF(codComp);
			arqComp.writeUTF(modelo);
			arqComp.writeUTF(processador);
			arqComp.writeInt(quantMemoria);
			arqComp.writeInt(tamanhoTela);
			arqComp.writeInt(quantEstoque);
			arqComp.writeFloat(preco);
			arqComp.writeInt(quantVendida);
			arqComp.writeUTF(dtUltimaVenda);
			arqComp.close();
			System.out.println("Dados gravados com sucesso !\n");
		} catch (IOException e) {
			System.out.println("Erro na abertura do arquivo  -  programa serÃ¡ finalizado. ");
			System.exit(0);
		}
	}

	public void desativarComputador(long posicao) {
// metodo para alterar o valor do campo ATIVO para N, tornando assim o registro
// excluido
		try {
			RandomAccessFile arqComp = new RandomAccessFile("COMP.DAT", "rw");
			arqComp.seek(posicao);
			arqComp.writeChar('N'); // desativar o registro antigo
			arqComp.close();
		} catch (IOException e) {
			System.out.println("Erro na abertura do arquivo  -  programa serÃ¡ finalizado");
			System.exit(0);
		}
	}

	public void registroDeVenda() {
		String codigo;
		int quantVendidaAux;
		String dataVendaAux;
		String confirmacao;

		Main.leia.nextLine();
		System.out.println("\n ***************  REGISTRO DE VENDA  ***************** ");

		System.out.println("Qual o cÃ³digo do computador?");
		codigo = Main.leia.nextLine();
		pesquisarComputador(codigo);

		System.out.println("Modelo.....: " + modelo);
		System.out.println("Processador............: " + processador);
		System.out.println("Quantidade de MemÃ³ria............: " + quantMemoria);
		System.out.println("Tamanho da Tela............: " + tamanhoTela);
		System.out.println("Quantidade no Estoque............: " + quantEstoque);
		System.out.println("Preço............: " + preco);
		System.out.println("QT VENDIDA............: " + quantVendida);

// nao sei se coloca isso daqui p baixo dentro do try catch ou nao
		do {
			System.out.println("Qual a quantidade vendida desse computador?");
			quantVendidaAux = Main.leia.nextInt();
		} while (quantVendidaAux < 0 && quantVendidaAux <= quantEstoque);

		Main.leia.nextLine();

		System.out.println("Digite a data da venda:");
		dataVendaAux = Main.leia.nextLine();

		do {
			System.out.println("Confirma o registro de venda? (SIM ou NÃO)");
			confirmacao = Main.leia.nextLine();
		} while (!confirmacao.equalsIgnoreCase("sim") && !confirmacao.equalsIgnoreCase("nÃ£o"));

		if (confirmacao.equalsIgnoreCase("sim")) {
			quantVendida = quantVendida + quantVendidaAux;
			quantEstoque = quantEstoque - quantVendida;
			dtUltimaVenda = dataVendaAux;
			incluirComputador();
		}

	}

// *********************** INCLUSAO *****************************
// DE BOA
	public void incluir() {
		String marcaAux;
		char confirmacao;
		int i;
		int j = 1;
		dtUltimaVenda = "";
		quantVendida = 0;
		String codigoComputador;
		String codigoNumerico = "";

		do {
			Main.leia.nextLine();
			System.out.println("\n ***************  INCLUSÃO DE COMPUTADORES  ***************** ");

			do {
				for (i = 0; i < 7; i++) {
					System.out.print(vetorMarcas[i] + " | ");
				}
				System.out.println();
				System.out.print("Digite a marca do computador.......................('FIM' para terminar)..: ");
				marcaAux = Main.leia.nextLine().toUpperCase();
				consistirMarca(marcaAux);
				if (marcaAux.equalsIgnoreCase("FIM")) {
					break;
				}

				codigoComputador = marcaAux.substring(0, 2) + "0001";

				try {
					RandomAccessFile arqComp = new RandomAccessFile("COMP.DAT", "rw");
					while (true) {
						ativo = arqComp.readChar();
						marca = arqComp.readUTF();
						codComp = arqComp.readUTF();
						modelo = arqComp.readUTF();
						processador = arqComp.readUTF();
						quantMemoria = arqComp.readInt();
						tamanhoTela = arqComp.readInt();
						quantEstoque = arqComp.readInt();
						preco = arqComp.readFloat();
						quantVendida = arqComp.readInt();
						dtUltimaVenda = arqComp.readUTF();

						if (marca.equalsIgnoreCase(marcaAux) && Integer.parseInt(codComp.substring(3)) >= Integer
								.parseInt(codigoComputador.substring(3)) && ativo == 'S') {
							codigoComputador = codigoComputador.substring(0, 2) + "000"
									+ Integer.toString(Integer.parseInt(codComp.substring(3)) + 1);
						}

					}

				} catch (EOFException e) { // conferir isso aqui, a mensagem
					codComp = codigoComputador;

					if (Integer.parseInt(codComp.substring(3)) + 1 >= 10) {
						codComp = codComp.replaceFirst("0", "");
					} else if (Integer.parseInt(codComp.substring(3)) + 1 >= 100) {
						codComp = codComp.replaceFirst("00", "0");
					} else if (Integer.parseInt(codComp.substring(3)) + 1 >= 1000) {
						codComp = codComp.replaceFirst("000", "");
					}

					System.out.println("Código do computador: " + codComp);

					// codComp = Main.leia.nextLine();
				} catch (IOException e) {
					System.out.println("Erro na abertura do arquivo - programa será finalizado.");
					System.exit(0);
				}

			} while (!consistirMarca(marcaAux));

			if (marcaAux.equalsIgnoreCase("FIM")) {
				break;
			}

			ativo = 'S';
			dtUltimaVenda = "";
			quantVendida = 0;
			marca = marcaAux;

			do {
				System.out.print("Digite o modelo.........................: ");
				modelo = Main.leia.nextLine();
			} while (modelo.length() < 1);

			do {
				for (i = 0; i < 6; i++) {
					System.out.print(vetorProcessadores[i] + " | ");
				}
				System.out.println();
				System.out.print("Digite o processador.........................: "); // METODO PARA PEGAR O PROCESSADOR
				processador = Main.leia.nextLine();
			} while (!consistirProcessador(processador));

			do {
				System.out.print("Digite a quantidade de memória (em GB).........................: ");
				quantMemoria = Main.leia.nextInt();
			} while (quantMemoria < 1 || quantMemoria > 16);

			do {
				for (i = 0; i < 6; i++) {
					System.out.print(vetorTamanhoTela[i] + " | ");
				}
				System.out.println();
				System.out.print("Digite o tamanho da tela.........................: "); // METODO PARA PEGAR O TAM DA
// TELA
				tamanhoTela = Main.leia.nextInt();
			} while (!consistirTamanhoTela(tamanhoTela));

			do {
				System.out.print("Digite a quantidade no estoque.........................: ");
				quantEstoque = Main.leia.nextInt();
			} while (quantEstoque < 0);

			do {
				System.out.print("Digite o preço do computador..................: ");
				preco = Main.leia.nextFloat();
			} while (preco < 1000 || preco > 20000);

// DELETEI OQ TAVA AQUI (UM TRY CATCH)

			do {
				System.out.print("\nConfirma a gravação dos dados (S/N) ? ");
				confirmacao = Main.leia.next().charAt(0);
				if (confirmacao == 'S') {
					incluirComputador();
				}
			} while (confirmacao != 'S' && confirmacao != 'N');

		} while (!marca.equals("FIM"));

	}

// ************************ ALTERACAO *****************************
	public void alterar() {
		String codigoComputador;
		char confirmacao;
		long posicaoRegistro = 0;
		byte opcao;

		do {
			do {
				Main.leia.nextLine();
				System.out.println("\n ***************  ALTERAÇÃO DE COMPUTADORES  ***************** ");
				System.out.print("Digite o códdigo do computador que deseja alterar( FIM para encerrar ): ");
				codigoComputador = Main.leia.nextLine();
				if (codigoComputador.equals("FIM")) {
					break;
				}

				posicaoRegistro = pesquisarComputador(codigoComputador);
				if (posicaoRegistro == -1) {
					System.out.println("Código não cadastrado no arquivo, digite outro valor.\n");
				}
			} while (posicaoRegistro == -1);

			if (codigoComputador.equals("FIM")) {
				break;
			}

			ativo = 'S';

			do {
				System.out.println("[ 1 ] Modelo.....: " + modelo);
				System.out.println("[ 2 ] Processador............: " + processador);
				System.out.println("[ 3 ] Quantidade de Memória............: " + quantMemoria);
				System.out.println("[ 4 ] Tamanho da Tela............: " + tamanhoTela);
				System.out.println("[ 5 ] Quantidade no Estoque............: " + quantEstoque);
				System.out.println("[ 6 ] Preço............: " + preco);

// FAZER TODAS AS VALIDACOES

				do {
					System.out
							.println("Digite o número do campo que deseja alterar (0 para finalizar as alterações): ");
					opcao = Main.leia.nextByte();
				} while (opcao < 0 || opcao > 6);
				if (opcao == 0) {
					break;
				}

				switch (opcao) {
				case 1:
					do {
						System.out.print("Digite o NOVO MODELO do computador: ");
						modelo = Main.leia.nextLine();
					} while (modelo.length() < 1);
					break;
				case 2:
					do {
						System.out.print("Digite o NOVO PROCESSADOR do computador...........: ");
						processador = Main.leia.nextLine();
					} while (!consistirProcessador(processador));
					break;
				case 3:
					do {
						System.out.print("Digite a NOVA QUANTIDADE DE MEMÓRIA do computador............: ");
						quantMemoria = Main.leia.nextInt();
					} while (quantMemoria < 1 || quantMemoria > 16);
					break;
				case 4:
					do {
						System.out.print("Digite o NOVO TAMANHO DA TELA do computador............: ");
						tamanhoTela = Main.leia.nextInt();
					} while (!consistirTamanhoTela(tamanhoTela));
					break;
				case 5:
					do {
						System.out.print("Digite a NOVA QUANTIDADE NO ESTOQUE do computador............: ");
						quantEstoque = Main.leia.nextInt();
					} while (quantEstoque < 0);
					break;
				case 6:
					do {
						System.out.print("Digite o NOVO PREÇO do computador............: ");
						preco = Main.leia.nextFloat();
					} while (preco < 1000 || preco > 20000);
					break;
				}
				System.out.println();
			} while (opcao != 0);

			do {
				System.out.print("\nConfirma a alteração dos dados (S/N) ? ");
				confirmacao = Main.leia.next().charAt(0);
				if (confirmacao == 'S') {
					desativarComputador(posicaoRegistro);
					incluirComputador();
					System.out.println("Dados gravados com sucesso !\n");
				}
			} while (confirmacao != 'S' && confirmacao != 'N');

		} while (!codComp.equals("FIM"));
	}

// ************************ EXCLUSAO *****************************
	public void excluir() {
		String codigoComputador;
		char confirmacao;
		long posicaoRegistro = 0;

		do {
			do {
				Main.leia.nextLine();
				System.out.println(" ***************  EXCLUSAO DE COMPUTADORES  ***************** ");
				System.out.print("Digite o CÓDIGO do computador que deseja excluir ( FIM para encerrar ): ");
				codigoComputador = Main.leia.nextLine();
				if (codigoComputador.equals("FIM")) {
					break;
				}

				posicaoRegistro = pesquisarComputador(codigoComputador);
				if (posicaoRegistro == -1) {
					System.out.println("Código não cadastrado no arquivo, digite outro valor.\n");
				}
			} while (posicaoRegistro == -1);

			if (codigoComputador.equals("FIM")) {
				System.out.println("\n ************  PROGRAMA ENCERRADO  ************** \n");
				break;
			}

			System.out.println("Marca: " + marca);
			System.out.println("Código: " + codComp);
			System.out.println("Modelo: " + modelo);
			System.out.println("Processador: " + processador);
			System.out.println("Quantidade de Memï¿½ria: " + quantMemoria);
			System.out.println("Tamanho da Tela: " + tamanhoTela);
			System.out.println("Quantidade no Estoque: " + quantEstoque);
			System.out.println("Preço: " + preco);
			System.out.println("Quantidade Vendida: " + quantVendida);
			System.out.println("Data da última Venda: " + dtUltimaVenda);
			System.out.println();

			do {
				System.out.print("\nConfirma a exclusão deste computador (S/N) ? ");
				confirmacao = Main.leia.next().charAt(0);
				if (confirmacao == 'S') {
					desativarComputador(posicaoRegistro);
				}
			} while (confirmacao != 'S' && confirmacao != 'N');

		} while (!codigoComputador.equals("FIM"));
	}

// ************************ CONSULTA *****************************
	public void consultar() {
		RandomAccessFile arqComp;
		byte opcao;
		String codigoComputador = null;
		long posicaoRegistro;

		do {
			do {
				System.out.println(" ***************  CONSULTA DE COMPUTADORES  ***************** ");
				System.out.println(" [1] LISTA DE TODOS OS COMPUTADORES ");
				System.out.println(" [2] LISTAR APENAS UM COMPUTADOR ATRAVÉS DO SEU CÓDIGO ");
				System.out.println(" [3] LISTAR SOMENTE COMPUTADORES JÁ VENDIDOS ");
				System.out.println(" [4] LISTAR COMPUTADORES CUJA ÚLTIMA VENDA OCORREU EM DETERMINADO MÊS/ANO");
				System.out.println(" [5] LISTAR COMPUTADORES POR FAIXA DE PREÇO");
				System.out.println(" [0] SAIR");
				System.out.print("\nDigite a opção desejada: ");
				opcao = Main.leia.nextByte();
				if (opcao < 0 || opcao > 5) {
					System.out.println("Opção Inválida, digite novamente.\n");
				}
			} while (opcao < 0 || opcao > 5);

			switch (opcao) {
			case 0:
				System.out.println("\n ************  PROGRAMA ENCERRADO  ************** \n");
				break;

			case 1: // imprime todos os computadores
				imprimirCabecalho();
				try {
					arqComp = new RandomAccessFile("COMP.DAT", "rw");
					while (true) {
						ativo = arqComp.readChar();
						marca = arqComp.readUTF();
						codComp = arqComp.readUTF();
						modelo = arqComp.readUTF();
						processador = arqComp.readUTF();
						quantMemoria = arqComp.readInt();
						tamanhoTela = arqComp.readInt();
						quantEstoque = arqComp.readInt();
						preco = arqComp.readFloat();
						quantVendida = arqComp.readInt();
						dtUltimaVenda = arqComp.readUTF();
						if (ativo == 'S') {
							imprimirComputador();
							total += preco * quantVendida;
						}

					}
					// arqComp.close();

				} catch (EOFException e) {
					System.out.println("\nTOTAL = " + total);
					System.out.println("\n FIM DE RELATÓRIO - ENTER para continuar...\n");
					Main.leia.nextLine();
					codComp = Main.leia.nextLine();
				} catch (IOException e) {
					System.out.println("Erro na abertura do arquivo - programa será finalizado");
					System.exit(0);
				}

				break;

			case 2: // imprime computador desejado atraves do seu codigo

				Main.leia.nextLine();

				System.out.print("\nDigite o código do computador desejado: ");
				codigoComputador = Main.leia.nextLine();

				posicaoRegistro = pesquisarComputador(codigoComputador);
				if (posicaoRegistro == -1) {
					System.out.println("\nComputador não encontrado. Tente novamente. \n");
				} else {
					imprimirCabecalho();
					imprimirComputador();
					System.out.println("\nFIM DE RELATÓRIO - ENTER PARA CONTINUAR...\n");
					Main.leia.nextLine();
				}
				break;

			case 3: // imprime apenas computadores já vendidos
				imprimirCabecalho();
				try {
					arqComp = new RandomAccessFile("COMP.DAT", "rw");
					while (true) {
						ativo = arqComp.readChar();
						marca = arqComp.readUTF();
						codComp = arqComp.readUTF();
						modelo = arqComp.readUTF();
						processador = arqComp.readUTF();
						quantMemoria = arqComp.readInt();
						tamanhoTela = arqComp.readInt();
						quantEstoque = arqComp.readInt();
						preco = arqComp.readFloat();
						quantVendida = arqComp.readInt();
						dtUltimaVenda = arqComp.readUTF();

						if (ativo == 'S' && quantVendida >= 1) {
							total += quantVendida * preco;
							imprimirComputador();
						} else if (arqComp.getFilePointer() == arqComp.length()) {
							break; // Encerra o loop quando atingir o final do arquivo
						}
					}
					// arqComp.close();

				} catch (EOFException e) {
					System.out.println("TOTAL : " + total);
					System.out.println("\n FIM DE RELATÓRIO - ENTER para continuar...\n");
					Main.leia.nextLine();
					codComp = Main.leia.nextLine();
				} catch (IOException e) {
					System.out.println("Erro na abertura do arquivo - programa será finalizado");
					System.exit(0);
				}
				break;

			case 4: // imprime apenas computadores cuja ultima venda foi em x mes e x ano
				String dtUltimaVendaAux;

				Main.leia.nextLine();
				System.out.println("Digite o MÊS e ANO que você deseja pesquisar (MM/AAAA):");
				dtUltimaVendaAux = Main.leia.nextLine();

				imprimirCabecalho();
				try {
					arqComp = new RandomAccessFile("COMP.DAT", "rw");
					while (true) {
						ativo = arqComp.readChar();
						marca = arqComp.readUTF();
						codComp = arqComp.readUTF();
						modelo = arqComp.readUTF();
						processador = arqComp.readUTF();
						quantMemoria = arqComp.readInt();
						tamanhoTela = arqComp.readInt();
						quantEstoque = arqComp.readInt();
						preco = arqComp.readFloat();
						quantVendida = arqComp.readInt();
						dtUltimaVenda = arqComp.readUTF();
						if (!dtUltimaVenda.equals("")) {
							if (dtUltimaVendaAux.equals(dtUltimaVenda.substring(3)) && ativo == 'S') {
								imprimirComputador();
							}
						}
						total += preco * quantVendida;
					}

					// arqComp.close();

				} catch (EOFException e) {
					System.out.println("TOTAL : " + total);
					System.out.println("\n FIM DE RELATÓRIO - ENTER para continuar...\n");
					Main.leia.nextLine();
					codComp = Main.leia.nextLine();
				} catch (IOException e) {
					System.out.println("Erro na abertura do arquivo - programa será finalizado");
					System.exit(0);
				}
				break;

			case 5: // listar computadores por faixa de preco, pedir preco min e max
				float precoMin;
				float precoMax;

				System.out.println("Digite o preço mínimo:");
				precoMin = Main.leia.nextFloat();
				System.out.println("Digite o preço máximo:");
				precoMax = Main.leia.nextFloat();

				imprimirCabecalho();

				try {
					arqComp = new RandomAccessFile("COMP.DAT", "rw");
					while (true) {
						ativo = arqComp.readChar();
						marca = arqComp.readUTF();
						codComp = arqComp.readUTF();
						modelo = arqComp.readUTF();
						processador = arqComp.readUTF();
						quantMemoria = arqComp.readInt();
						tamanhoTela = arqComp.readInt();
						quantEstoque = arqComp.readInt();
						preco = arqComp.readFloat();
						quantVendida = arqComp.readInt();
						dtUltimaVenda = arqComp.readUTF();
						if (preco >= precoMin && preco <= precoMax && ativo == 'S') {
							imprimirComputador();
							total += preco * quantVendida;
						}

					}

				} catch (EOFException e) {
					System.out.println("TOTAL : " + total);
					System.out.println("\n FIM DE RELATÓRIO - ENTER para continuar...\n");
					Main.leia.nextLine();
					codComp = Main.leia.nextLine();
				} catch (IOException e) {
					System.out.println("Erro na abertura do arquivo - programa será finalizado");
					System.exit(0);
				}
				break;
			}

		} while (opcao != 0);
	}

// revisar forma display
	public void imprimirCabecalho() {
		System.out
				.print("CÓDIGO---MARCA----MODELO-------PROCESSADOR------QUANT ESTOQUE----PREÇO-----QUANT VENDIDA----");
		System.out.println("DT ULTIMA VENDA------VLR TOTAL  ");
	}

// corrigido
	public void imprimirComputador() {
		System.out.println(formatarString(codComp, 7) + "  " + formatarString(marca, 7) + "  "
				+ formatarString(modelo, 10) + "  " + formatarString(processador, 20) + "  "
				+ formatarString(String.valueOf(quantEstoque), 10) + "  " + formatarString(String.valueOf(preco), 15)
				+ "  " + formatarString(String.valueOf(quantVendida), 11) + "  " + formatarString(dtUltimaVenda, 19)
				+ "  " + formatarString(String.valueOf(preco * quantVendida), 10));

	}

	public static String formatarString(String texto, int tamanho) {
// retorna uma string com o numero de caracteres passado como parametro em
// TAMANHO
		if (texto.length() > tamanho) {
			texto = texto.substring(0, tamanho);
		} else {
			while (texto.length() < tamanho) {
				texto = texto + " ";
			}
		}
		return texto;
	}
}
