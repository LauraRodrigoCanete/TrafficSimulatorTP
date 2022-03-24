package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import extra.jtable.EventEx;
import extra.jtable.EventsTableModel;
import extra.jtable.JTableExamples;
import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.SetWeatherEvent;
import simulator.model.TrafficSimObserver;
import simulator.model.Weather;

public class StatusBar extends JPanel implements TrafficSimObserver {
	private Border _defaultBorder = BorderFactory.createLineBorder(Color.red, 1);

	// this is what we show in the table
	// esto es lo que mostramos en la table
	private List<Event> _events;
	private int _time;
	
	
	public StatusBar(List<Event>events) {
		super();
		_events=events;
		_time=1;
		initGUI();
	}
	public void initGUI() {
		JFrame f = new JFrame();
        JPanel p = new JPanel(new BorderLayout());
		
		JLabel l1= new JLabel("Time: "+String.valueOf(_time));
		JLabel l2= new JLabel("Event Added("+_events.get(_time-1).toString()+")");
		
		
		p.add(l1, BorderLayout.WEST);
		p.add(l2, BorderLayout.EAST);

		f.add(p);
		f.setSize(20, 300);
		setVisible(true);

		
		
		

		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(700, 300);
		//pack();
		setVisible(true);
	}
	public void setEventsList(List<Event> events) {
		_events = events;
		//fireTableDataChanged();
	}
	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				List<Event> list=new ArrayList();
				List<Pair<String,Weather>> ws = new ArrayList();
				Pair par=new Pair("Hola", Weather.CLOUDY);
				ws.add(par);
				Event e=new SetWeatherEvent(1, ws);
				list.add(e);
				new StatusBar(list);
			}
		});
	}
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}

}
