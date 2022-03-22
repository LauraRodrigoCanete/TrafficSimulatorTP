package simulator.view;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class EventsTableModel extends AbstractTableModel implements TrafficSimObserver {

	private Controller controller;
	private List<Event> _events;
	private String[] _colNames = { "Time", "Desc" };

	public EventsTableModel(Controller controller) {
		_events=null;
		this.controller = controller;
		//controller.addObserver(this);
		//this.setVisible(true);
	}
	
	public void setEventsList(List<Event> events) {
		_events = events;
		fireTableDataChanged();	
	}

	@Override
	public boolean isCellEditable(int row, int column) {//no puedes modificar el dato de la casilla
		return false;
	}

	//si no pongo esto no coge el nombre de las columnas
	@Override
	public String getColumnName(int col) {
		return _colNames[col];
	}

	@Override
	// metodo obligatorio
	public int getColumnCount() {
		return _colNames.length;
	}

	@Override
	// metodo obligatorio
	public int getRowCount() {
		return _events == null ? 0 : _events.size();//no puedo hacer null.size()
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {//no le llamaremos es para ver como se visualiza cada casilla (fila,col)
		//quiero cada evento en una fila (el de la pr es igual q este)
		Object s = null;
		switch (columnIndex) {//cada case es una columna
		case 0:
			s = _events.get(rowIndex).getTime();
			break;
		case 1:
			s = _events.get(rowIndex).toString();
			break;
		}
		return s;
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		fireTableDataChanged();//si cambia el tiempo se pueden haber a�adido m�s eventos
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