package main;
// Class: Clock Listener
// Written by: Mr. Swope
// Date: 1/20/2022
// Description: ClockListener implementation
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ClockListener implements ActionListener {

	GraphicsPanel f;

	ClockListener(GraphicsPanel c)
	{
		f = c;
	}

	public void actionPerformed(ActionEvent e) {
		try {
			f.clock();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
