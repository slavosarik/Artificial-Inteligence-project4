package logic;

import java.util.ArrayList;
import java.util.List;

public class Pravidlo {

	String meno;
	List<String> podmienky;
	List<String> akcie;

	public Pravidlo() {
		podmienky = new ArrayList<>();
		akcie = new ArrayList<>();
	}

}
