package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.SetContClassEvent;
import simulator.model.SetWeatherEvent;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;
import simulator.model.Weather;

public class ControlPanel extends JPanel implements TrafficSimObserver{
	
	private Controller _ctrl;
	private boolean _stopped;
	private JToolBar barra;
	private JButton stop;
	private JButton run;
	private JButton contClass;
	private JButton weather;
	private JSpinner ticks;
	
	private List<Road> _roads;//estas cosas habra q actualizarlas en los metodos de observador
	private List<Vehicle> _vehicles;
	private List<Event> _events;
	private int _time;
	
	public ControlPanel(Controller ctrl) {
		_roads = new ArrayList<>();
		_vehicles = new ArrayList<>();
		_events = new ArrayList<>();;
		_time = 0;
		_stopped = false;
		_ctrl = ctrl;
		initGUI();
		_ctrl.addObserver(this);
	}
	
	private void initGUI() {
		contClass = new JButton();
		weather = new JButton();
		run = new JButton();
		ticks = new JSpinner(new SpinnerNumberModel(10, 1, 10000, 1));
		stop = new JButton();
		barra = new JToolBar();
		this.setLayout(new BorderLayout());//viene por defecto
		this.add(barra, BorderLayout.NORTH);
		
		//boton contClass
		
		contClass.setActionCommand("contClass");//no sirve de nada, ya est� por defecto
		contClass.setToolTipText("Change contamination class");
		contClass.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent event) {
						ChangeContDialog dialog = new ChangeContDialog(getPreviousFrame());

						//sin esta linea no es visible, se hace visible en el open
						int status = dialog.open(_vehicles);
						if (status == 0) {
							System.out.println("Canceled");
						} else {
							List<Pair<String,Integer>> pairs = new ArrayList<Pair<String,Integer>>();
							pairs.add(dialog.getVehicleandClass());
							_events.add(new SetContClassEvent(_time + dialog.getTicks(), pairs));
						}
					}
				});
				contClass.setIcon(new ImageIcon("resources/icons/co2class.png"));
				barra.add(contClass);
		
			
			
		//boton weather
		weather.setActionCommand("weather");//no sirve de nada, ya est� por defecto
		weather.setToolTipText("Change the weather conditions");
		weather.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				ChangeWeatherDialog dialog = new ChangeWeatherDialog(getPreviousFrame());

				//sin esta linea no es visible, se hace visible en el open
				int status = dialog.open(_roads);
				if (status == 0) {
					System.out.println("Canceled");
				} else {
					List<Pair<String,Weather>> pairs = new ArrayList<Pair<String,Weather>>();
					pairs.add(dialog.getRoadandWeather());
					_events.add(new SetWeatherEvent(_time + dialog.getTicks(), pairs));
				}
			}
		});
		weather.setIcon(new ImageIcon("resources/icons/weather.png"));
		barra.add(weather);
		
		//boton run
		run.setActionCommand("run");
		run.setToolTipText("Run the program");
		run.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				_stopped = false;
				enableToolBar(false);
				Integer t = (Integer) ticks.getValue();
				run_sim(t);
			}
		});
		run.setIcon(new ImageIcon("resources/icons/run.png"));
		barra.add(run);
		//barra.addSeparator();
		
		//boton stop
		stop.setActionCommand("stop");
		stop.setToolTipText("Stop the program");
		stop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				stop();
			}
		});
		stop.setIcon(new ImageIcon("resources/icons/stop.png"));
		barra.add(stop);
		
		//spinner ticks
		ticks.setToolTipText("Simulation tick to run: 1-10000");
		ticks.setMaximumSize(new Dimension(80, 40));
		ticks.setMinimumSize(new Dimension(80, 40));
		ticks.setPreferredSize(new Dimension(80, 40));
		barra.add( new JLabel("Ticks: "));//se a�ade todo al toolbar
		barra.add(ticks);
		
		//separacion
		barra.add(Box.createGlue());
		
		//boton salir
		
	}
	
	private void run_sim(int n) {
		if (n > 0 && !_stopped) {
			try {
				_ctrl.run(1);
			} catch (Exception e) {
				// TODO show error message
				_stopped = true;
				return;
			}
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					run_sim(n - 1);
				}
			});
		} else {
			
			enableToolBar(true);//para que puedas pulsar el resto de botones
			_stopped = true;
		}
	}
	
	private void enableToolBar(boolean b) {
		//poner boton por boton pq toda la toolbar de golpe no funciona bien
		run.setEnabled(b);
		stop.setEnabled(true);
	}

	private void stop() {
		_stopped = true;
	}

	/*
	 * //lo tengo q hacer fuera para q sea un metodo 
	 * de la clase JPnanel pq dentro de la clase anonima el this cambia y 
	 * no me deja usar el metodo
	 */
	private Frame getPreviousFrame() {
		return (Frame) SwingUtilities.getWindowAncestor(this);
	}
	
	//en estos metodos necesitamos ir reasignando las listas de vehiculos, junc... q tengamos como atributos
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		
	}
	
	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		
	}
	
	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {

	}
	
	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {

	}
	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {

	}
	
	@Override
	public void onError(String err) {

	}


}
