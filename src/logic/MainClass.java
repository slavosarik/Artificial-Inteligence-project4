package logic;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainClass {

	List<String> pamat;
	List<Pravidlo> bazaPravidiel;
	List<String[]> aplikovatelneInstancieAkcii;
	Windows mainWindows;
	List<List<String>> filtrovaneInstanciePravidiel;
	List<String> vypisaneSpravy;

	public void init() {

		bazaPravidiel = new ArrayList<>();
		pamat = new ArrayList<>();
		mainWindows = new Windows();
		vypisaneSpravy = new ArrayList<>();

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					mainWindows.getFrame().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		mainWindows.getBtnNacitanieFaktov().addActionListener(
				new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						// pracovna pamat
						pamat = new ArrayList<>();
						BufferedReader br = null;
						try {
							br = new BufferedReader(new InputStreamReader(
									new FileInputStream("data/pamat.txt")));
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}
						try {
							String line;
							try {
								while ((line = br.readLine()) != null) {
									pamat.add(line);
								}
							} catch (IOException e) {
								e.printStackTrace();
							}
						} finally {
							try {
								br.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}

						// pridat do text area pre pamat

						StringBuilder builder = new StringBuilder();
						for (String s : pamat)
							builder.append(s).append("\n");

						mainWindows.getTextAreaPamat().setText(
								builder.toString());
						mainWindows.getTextAreaPamat().setCaretPosition(0);

					}
				});

		mainWindows.getBtnNacitaniePravidiel().addActionListener(
				new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

						// baza pravidiel
						bazaPravidiel = new ArrayList<>();
						BufferedReader br1 = null;
						try {
							br1 = new BufferedReader(new InputStreamReader(
									new FileInputStream("data/baza.txt")));
						} catch (FileNotFoundException e1) {
							e1.printStackTrace();
						}
						try {
							String line;
							try {
								while ((line = br1.readLine()) != null) {

									Pravidlo p = new Pravidlo();
									p.meno = line.substring(6);

									line = br1.readLine();
									String podmienka = line.substring(3);
									String podmienky[] = podmienka.split(",");

									for (String podm : podmienky)
										p.podmienky.add(podm);

									line = br1.readLine();
									String akcia = line.substring(6);
									String akcie[] = akcia.split(",");

									for (String akc : akcie)
										p.akcie.add(akc);

									bazaPravidiel.add(p);

									line = br1.readLine();

								}
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						} finally {
							try {
								br1.close();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}

						// pridat do text area

						StringBuilder builder = new StringBuilder();
						for (Pravidlo prav : bazaPravidiel) {
							builder.append("Meno: ").append(prav.meno)
									.append("\n").append("AK ");
							for (String s : prav.podmienky) {
								builder.append(s);
								if (prav.podmienky.indexOf(s) != prav.podmienky
										.size() - 1)
									builder.append(",");
							}
							builder.append("\n").append("AK ");
							for (String s : prav.akcie) {
								builder.append(s);
								if (prav.akcie.indexOf(s) != prav.akcie.size() - 1)
									builder.append(",");
							}
							builder.append("\n\n");
						}
						mainWindows.getTextAreaBaza().setText(
								builder.toString());
						mainWindows.getTextAreaBaza().setCaretPosition(0);

					}
				});

		mainWindows.getBtnVsetkyKroky().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				operate(bazaPravidiel);
			}
		});

		mainWindows.getBtn1Krok().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				mainWindows.getBtnVsetkyKroky().setEnabled(false);
				operateSoloStep(bazaPravidiel);
			}
		});

		mainWindows.getBtnClear().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				mainWindows.getTextAreaVypis().setText("");
				vypisaneSpravy = new ArrayList<>();
				mainWindows.getBtn1Krok().setEnabled(true);
				mainWindows.getBtnVsetkyKroky().setEnabled(true);
				mainWindows.getTextAreaFilter().setText("");
			}
		});

	}

	List<String[]> vytvorKopiu1(List<String[]> naviazania) {

		List<String[]> list = new ArrayList<>();

		for (String[] prvok : naviazania) {

			String[] novy = new String[prvok.length];

			for (int i = 0; i < prvok.length; i++) {
				novy[i] = new String(prvok[i]);
			}

			list.add(novy);
		}

		return list;
	}

	List<String> vytvorKopiu2(List<String> inputList) {

		List<String> list = new ArrayList<>();

		for (String prvok : inputList) {

			String novy = new String(prvok);

			list.add(novy);
		}

		return list;
	}

	public void prehladavaj(String meno, List<String> podmienky,
			List<String> akcie, List<String[]> naviazania) {

		if (podmienky.isEmpty() == true) {
			// ak sa vsetky podmienky naviazali na fakty, tak prehladavame akcie
			// a vytvarame instaciu pravidla
			String[] instanciePravidiel = new String[akcie.size()];
			int pocetAkciiInstancie = 0;

			for (String akcia : akcie) {

				String[] fakt = akcia.split(" ");

				// zistujem ci sa podmienka podoba na fakt
				for (int i = 0; i < fakt.length; i++) {

					if (fakt[i].startsWith("?") == true) {
						String name = fakt[i];
						for (String[] naviazanie : naviazania)
							if (name.equals(naviazanie[0]) == true)
								fakt[i] = naviazanie[1];
					}
				}

				String novyFakt = "";
				for (int i = 0; i < fakt.length; i++) {
					if ("".equals(novyFakt) == false)
						novyFakt += " ";
					novyFakt += fakt[i];
				}
				instanciePravidiel[pocetAkciiInstancie] = novyFakt;
				pocetAkciiInstancie++;
			}

			aplikovatelneInstancieAkcii.add(instanciePravidiel);
			return;

		}

		// podmienka = fakt, ktory chcem naviazat
		String[] podmienka = podmienky.get(0).split(" ");

		if ("<>".equals(podmienka[0]) == true) {

			String op1 = podmienka[1];
			String op2 = podmienka[2];
			String name1 = null, name2 = null;

			for (String[] nav : naviazania) {
				if (op1.equals(nav[0]) == true)
					name1 = nav[1];
				if (op2.equals(nav[0]) == true)
					name2 = nav[1];
			}

			if (name1.equals(name2) == false) {
				List<String> novePodmienky = vytvorKopiu2(podmienky);
				novePodmienky.remove(0);

				prehladavaj(meno, novePodmienky, akcie, naviazania);
			}

		} else

			for (int x = 0; x < pamat.size(); x++) {

				// fakt = polozka pamate
				String[] fakt = pamat.get(x).split(" ");

				boolean zhoda = true;

				// zistujem ci sa podmienka podoba na fakt
				for (int i = 0; i < fakt.length; i++) {

					// kontrolujem, ci sa podoba a ak sa vobec nepodoba, tak
					// zhoda =
					// false a koncim s tymto faktom
					if (podmienka[i].startsWith("?") == false
							&& podmienka[i].equals(fakt[i]) == false) {
						zhoda = false;
					}

					if (zhoda == false)
						break;
				}

				// zhoda podobnosti sa nenasla, koncim s aktualnym faktom
				if (zhoda == false)
					continue;

				List<String[]> noveNaviazania = new ArrayList<>();

				// zistujem ci mozem dosadit hodnoty za premenne
				for (int i = 0; i < fakt.length; i++) {
					if (podmienka[i].startsWith("?") == true) {

						// naviazanie - prva pozicia "?X" a druha pozicia napr.
						// "Peter"
						String[] naviazanie = new String[] { podmienka[i],
								fakt[i] };
						noveNaviazania.add(naviazanie);

					}
				}

				// ziskal som nove naviazania - list nove Naviazania, teraz
				// pozriem,
				// ci uz take existuju a
				// ak existuju, ci su totozne - pokial ano, idem rekurzivne do
				// vnutra dalsej
				// podmienky, v opacnom pripade koncim s aktualnym faktom a idem
				// prehladat dalsie

				boolean vhodne = true;

				for (String[] naviazanie : naviazania) {

					for (int i = 0; i < noveNaviazania.size(); i++) {

						if (naviazanie[0].equals(noveNaviazania.get(i)[0]) == true
								&& naviazanie[1]
										.equals(noveNaviazania.get(i)[1]) == true) {
							noveNaviazania.remove(i);
							i--;
							continue;

						} else if (naviazanie[0]
								.equals(noveNaviazania.get(i)[0]) == true
								|| naviazanie[1]
										.equals(noveNaviazania.get(i)[1]) == true) {
							// zle, preskocit pravidlo
							vhodne = false;
							break;// asi
						}

					}

				}

				if (vhodne == false)
					continue;

				List<String[]> updatnuteNaviazania = vytvorKopiu1(naviazania);

				for (String[] nadv : noveNaviazania) {
					updatnuteNaviazania.add(nadv);
				}

				List<String> novePodmienky = vytvorKopiu2(podmienky);
				novePodmienky.remove(0);

				// rekurzivne sa vnorim do dalsej podmienky pravidla
				prehladavaj(meno, novePodmienky, akcie, updatnuteNaviazania);

			}
	}

	public void refreshPamatTextArea() {

		StringBuilder builder = new StringBuilder();

		for (String s : pamat)
			builder.append(s).append("\n");

		mainWindows.getTextAreaPamat().setText(builder.toString());
		mainWindows.getTextAreaPamat().setCaretPosition(0);

	}

	public void pridaj(String akcia) {

		if (pamat.contains(akcia)) {
			System.out.println("Duplicita v pamati: " + akcia);
		} else {

			System.out.println("Pridavam: " + akcia);
			pamat.add(akcia);
			refreshPamatTextArea();
		}
	}

	public void vymaz(String akcia) {

		if (pamat.contains(akcia)) {
			System.out.println("Vymazavam: " + akcia);
			pamat.remove(akcia);
			refreshPamatTextArea();
		} else {
			System.out.println("Nemozem vymazat, fakt neexistuje: " + akcia);
		}

	}

	public void sprava(String akcia) {

		System.out.println("Sprava: " + akcia);
		mainWindows.getTextAreaVypis().append(akcia + "\n");
		mainWindows.getTextAreaVypis().setCaretPosition(0);

	}

	public void operate(List<Pravidlo> pravidla) {

		mainWindows.getTextAreaVypis().setText("");
		vypisaneSpravy = new ArrayList<>();

		do {

			aplikovatelneInstancieAkcii = new ArrayList<>();

			// ziskavanie aplikovatelnych instancii
			for (Pravidlo pravidlo : pravidla) {
				List<String[]> naviazania = new ArrayList<>();
				prehladavaj(pravidlo.meno, pravidlo.podmienky, pravidlo.akcie,
						naviazania);
			}

			// filter aplikovatelnych instancii

			filtrovaneInstanciePravidiel = new ArrayList<>();

			for (String[] inst : aplikovatelneInstancieAkcii) {

				List<String> filtrovaneInstanciaPravidla = new ArrayList<>();

				for (String s : inst) {

					String prikaz = s.substring(0, s.indexOf(" "));

					String spracovanyFakt = s.substring(s.indexOf(" ") + 1,
							s.length());

					if ("pridaj".equals(prikaz)) {

						if (pamat.contains(spracovanyFakt) == false) {
							String filtrovanyFakt = prikaz + " "
									+ spracovanyFakt;
							filtrovaneInstanciaPravidla.add(filtrovanyFakt);
						}

					} else if ("vymaz".equals(prikaz)) {

						if (pamat.contains(spracovanyFakt) == true) {
							String filtrovanyFakt = prikaz + " "
									+ spracovanyFakt;
							filtrovaneInstanciaPravidla.add(filtrovanyFakt);
						}

					} else {
						// pre spravu
						if (vypisaneSpravy.contains(spracovanyFakt) == false) {
							String filtrovanyFakt = prikaz + " "
									+ spracovanyFakt;
							filtrovaneInstanciaPravidla.add(filtrovanyFakt);
						}
					}

				}
				if (filtrovaneInstanciaPravidla.isEmpty() == false)
					filtrovaneInstanciePravidiel
							.add(filtrovaneInstanciaPravidla);
			}

			// vykonanie

			System.out.println("Vypis aplikovatelnych instancii"
					+ "   velkost: " + aplikovatelneInstancieAkcii.size());

			for (String[] inst : aplikovatelneInstancieAkcii) {
				for (String s : inst) {
					System.out.print(s + "...");
				}
				System.out.println();
			}

			System.out.println("Vypis filtrovanych instancii" + "   velkost: "
					+ filtrovaneInstanciePravidiel.size());

			StringBuilder builder = new StringBuilder();
			builder.append("Vypis filtrovanych instancii, velkost: ")
					.append(filtrovaneInstanciePravidiel.size()).append("\n");
			for (List<String> inst : filtrovaneInstanciePravidiel) {
				for (String s : inst) {
					builder.append(s).append(", ");
					System.out.print(s + "...");
				}
				builder.append("\n");
				System.out.println();
			}
			mainWindows.getTextAreaFilter().setText(builder.toString());
			mainWindows.getTextAreaFilter().setCaretPosition(0);

			System.out.println("VELKOST FILTROVANYCH INST: "
					+ filtrovaneInstanciePravidiel.size());
			if (filtrovaneInstanciePravidiel.size() > 0) {
				for (String s : filtrovaneInstanciePravidiel.get(0)) {

					String prikaz = s.substring(0, s.indexOf(" "));

					String akcia = s.substring(s.indexOf(" ") + 1, s.length());

					if ("pridaj".equals(prikaz)) {
						System.out
								.println("=====================AKCIA PRIDAJ: "
										+ akcia);
						pridaj(akcia);
					} else if ("vymaz".equals(prikaz)) {
						System.out.println("=====================AKCIA VYMAZ: "
								+ akcia);
						vymaz(akcia);
					} else if ("sprava".equals(prikaz)) {
						vypisaneSpravy.add(akcia);
						System.out
								.println("=====================AKCIA SPRAVA: "
										+ akcia);
						sprava(akcia);
					}

				}
			}

		} while (filtrovaneInstanciePravidiel.size() > 0);
	}

	public void operateSoloStep(List<Pravidlo> pravidla) {

		aplikovatelneInstancieAkcii = new ArrayList<>();

		// ziskavanie aplikovatelnych instancii
		for (Pravidlo pravidlo : pravidla) {
			List<String[]> naviazania = new ArrayList<>();
			prehladavaj(pravidlo.meno, pravidlo.podmienky, pravidlo.akcie,
					naviazania);
		}

		// filter aplikovatelnych instancii

		filtrovaneInstanciePravidiel = new ArrayList<>();

		for (String[] inst : aplikovatelneInstancieAkcii) {

			List<String> filtrovaneInstanciaPravidla = new ArrayList<>();

			for (String s : inst) {

				String prikaz = s.substring(0, s.indexOf(" "));

				String spracovanyFakt = s.substring(s.indexOf(" ") + 1,
						s.length());

				if ("pridaj".equals(prikaz)) {

					if (pamat.contains(spracovanyFakt) == false) {
						String filtrovanyFakt = prikaz + " " + spracovanyFakt;
						filtrovaneInstanciaPravidla.add(filtrovanyFakt);
					}

				} else if ("vymaz".equals(prikaz)) {

					if (pamat.contains(spracovanyFakt) == true) {
						String filtrovanyFakt = prikaz + " " + spracovanyFakt;
						filtrovaneInstanciaPravidla.add(filtrovanyFakt);
					}

				} else {
					// pre spravu
					if (vypisaneSpravy.contains(spracovanyFakt) == false) {
						String filtrovanyFakt = prikaz + " " + spracovanyFakt;
						filtrovaneInstanciaPravidla.add(filtrovanyFakt);
					}
				}

			}
			if (filtrovaneInstanciaPravidla.isEmpty() == false)
				filtrovaneInstanciePravidiel.add(filtrovaneInstanciaPravidla);
		}

		// vykonanie

		System.out.println("Vypis aplikovatelnych instancii" + "   velkost: "
				+ aplikovatelneInstancieAkcii.size());

		for (String[] inst : aplikovatelneInstancieAkcii) {
			for (String s : inst) {
				System.out.print(s + "...");
			}
			System.out.println();
		}

		System.out.println("Vypis filtrovanych instancii" + "   velkost: "
				+ filtrovaneInstanciePravidiel.size());

		StringBuilder builder = new StringBuilder();
		builder.append("Vypis filtrovanych instancii, velkost: ")
				.append(filtrovaneInstanciePravidiel.size()).append("\n");
		for (List<String> inst : filtrovaneInstanciePravidiel) {
			for (String s : inst) {
				builder.append(s).append(", ");
				System.out.print(s + "...");
			}
			builder.append("\n");
			System.out.println();
		}
		mainWindows.getTextAreaFilter().setText(builder.toString());
		mainWindows.getTextAreaFilter().setCaretPosition(0);

		System.out.println("VELKOST FILTROVANYCH INST: "
				+ filtrovaneInstanciePravidiel.size());
		if (filtrovaneInstanciePravidiel.size() > 0) {
			for (String s : filtrovaneInstanciePravidiel.get(0)) {

				String prikaz = s.substring(0, s.indexOf(" "));

				String akcia = s.substring(s.indexOf(" ") + 1, s.length());

				if ("pridaj".equals(prikaz)) {
					System.out.println("=====================AKCIA PRIDAJ: "
							+ akcia);
					pridaj(akcia);
				} else if ("vymaz".equals(prikaz)) {
					System.out.println("=====================AKCIA VYMAZ: "
							+ akcia);
					vymaz(akcia);
				} else if ("sprava".equals(prikaz)) {
					vypisaneSpravy.add(akcia);
					System.out.println("=====================AKCIA SPRAVA: "
							+ akcia);
					sprava(akcia);
				}

			}
		}

		if (filtrovaneInstanciePravidiel.size() == 0)
			mainWindows.getBtn1Krok().setEnabled(false);
	}

	public static void main(String[] args) {

		MainClass mc = new MainClass();
		mc.init();
	}

}
