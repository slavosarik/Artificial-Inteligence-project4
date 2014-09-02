package logic;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Windows {

	private JFrame frame;
	private JButton btnNewButton;
	private JButton btnNataniePravidiel;
	private JButton btnVetkyKroky;
	private JButton btnKrok;
	private JTextArea textArea;
	private JTextArea textArea_1;
	private JTextArea textArea_2;
	private JTextArea textArea_3;
	private JButton btnClear;

	/**
	 * Create the application.
	 */
	public Windows() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		} catch (InstantiationException e) {

			e.printStackTrace();
		} catch (IllegalAccessException e) {

			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {

			e.printStackTrace();
		}

		frame = new JFrame();
		frame.setBounds(100, 100, 892, 591);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 55, 332, 158);
		frame.getContentPane().add(scrollPane);

		textArea = new JTextArea();
		textArea.setFont(new Font("Franklin Gothic Book", Font.PLAIN, 12));
		scrollPane.setViewportView(textArea);

		JLabel lblPamSFaktami = new JLabel("Pam\u00E4\u0165 s faktami");
		lblPamSFaktami
				.setFont(new Font("Franklin Gothic Book", Font.PLAIN, 16));
		lblPamSFaktami.setBounds(10, 11, 197, 33);
		frame.getContentPane().add(lblPamSFaktami);

		btnNewButton = new JButton("Na\u010D\u00EDtanie faktov");
		btnNewButton.setBounds(218, 18, 124, 23);
		frame.getContentPane().add(btnNewButton);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(363, 55, 503, 158);
		frame.getContentPane().add(scrollPane_1);
		
		textArea_1 = new JTextArea();
		textArea_1.setFont(new Font("Franklin Gothic Book", Font.PLAIN, 12));
		scrollPane_1.setViewportView(textArea_1);

		JLabel lblBzaPravidie = new JLabel("B\u00E1za pravidiel");
		lblBzaPravidie
				.setFont(new Font("Franklin Gothic Book", Font.PLAIN, 16));
		lblBzaPravidie.setBounds(363, 15, 268, 25);
		frame.getContentPane().add(lblBzaPravidie);

		btnNataniePravidiel = new JButton("Na\u010D\u00EDtanie pravidiel");
		btnNataniePravidiel.setBounds(716, 18, 150, 23);
		frame.getContentPane().add(btnNataniePravidiel);

		JLabel lblFiltrovanIntanciePravidiel = new JLabel(
				"Filtrovan\u00E9 in\u0161tancie pravidiel");
		lblFiltrovanIntanciePravidiel.setFont(new Font("Franklin Gothic Book",
				Font.PLAIN, 16));
		lblFiltrovanIntanciePravidiel.setBounds(10, 235, 332, 33);
		frame.getContentPane().add(lblFiltrovanIntanciePravidiel);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 268, 332, 137);
		frame.getContentPane().add(scrollPane_2);
		
		textArea_2 = new JTextArea();
		textArea_2.setFont(new Font("Franklin Gothic Book", Font.PLAIN, 12));
		scrollPane_2.setViewportView(textArea_2);

		JLabel lblVpis = new JLabel("V\u00FDpis");
		lblVpis.setFont(new Font("Franklin Gothic Book", Font.PLAIN, 16));
		lblVpis.setBounds(363, 224, 161, 33);
		frame.getContentPane().add(lblVpis);

		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(363, 268, 503, 137);
		frame.getContentPane().add(scrollPane_3);
		
		textArea_3 = new JTextArea();
		textArea_3.setFont(new Font("Franklin Gothic Book", Font.PLAIN, 12));
		scrollPane_3.setViewportView(textArea_3);

		btnKrok = new JButton("1 krok");
		btnKrok.setBounds(623, 518, 114, 23);
		frame.getContentPane().add(btnKrok);

		btnVetkyKroky = new JButton("V\u0161etky kroky");
		btnVetkyKroky.setBounds(747, 518, 119, 23);
		frame.getContentPane().add(btnVetkyKroky);

		btnClear = new JButton("Clear");
		btnClear.setBounds(515, 518, 98, 23);
		frame.getContentPane().add(btnClear);
	}

	public JButton getBtnNacitanieFaktov() {
		return btnNewButton;
	}

	public JButton getBtnNacitaniePravidiel() {
		return btnNataniePravidiel;
	}

	public JButton getBtnVsetkyKroky() {
		return btnVetkyKroky;
	}

	public JButton getBtn1Krok() {
		return btnKrok;
	}

	public JFrame getFrame() {
		return frame;
	}
	public JTextArea getTextAreaPamat() {
		return textArea;
	}

	public JTextArea getTextAreaBaza() {
		return textArea_1;
	}
	public JTextArea getTextAreaFilter() {
		return textArea_2;
	}
	public JTextArea getTextAreaVypis() {
		return textArea_3;
	}
	public JButton getBtnClear() {
		return btnClear;
	}
}
