package mbeans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleModel;

import control.ControleAdmin;
import control.ControleDisciplina;
import control.ControleEnsalamento;
//import control.ControleEscola;
import entidades.Curso;
import entidades.Dia;
import entidades.Disciplina;
import entidades.Aula;
import entidades.Professor;
import entidades.Sala;
import entidades.Turma;
import entidades.Turno;

@ManagedBean(name = "ensalamentoMBean")
@SessionScoped
public class EnsalamentoMBean {

	private ControleEnsalamento controleEnsalamento;
	private ScheduleModel eventModel;
	

	public EnsalamentoMBean() {
		eventModel = new DefaultScheduleModel();
		Curso curso1 = new Curso("inf1", 1, null, null);
		Turma turma1 = new Turma(1, "t1", curso1, 1);
		Sala sala1 = new Sala("sala1", 1);
		Disciplina disciplina1 = new Disciplina("POO2", 1,new ArrayList<Professor>());
		Professor professor1 = new Professor("Daniel",new ArrayList<Disciplina>(), 1);
		Turno turno = new Turno("turno1");
		Dia dia =new Dia("segunda");
		Aula aula = new Aula(professor1,turma1,sala1,turno,curso1,disciplina1,dia,turno,1);
		adicionarEvento(aula.texto2(), GregorianCalendar.FRIDAY);

		adicionarEvento("teste \n teste2", GregorianCalendar.MONDAY);
//		adicionarAulas();
	}

	// TODO FALAR DANIEL M�TODO NAO EST� ADICIONANDO EVENTO
	private void adicionarEvento(String descricao,
			int diaSemanaGregorianCalendar) {
		GregorianCalendar calendarioInicio = (GregorianCalendar) GregorianCalendar
				.getInstance();
		calendarioInicio.set(GregorianCalendar.DAY_OF_WEEK,
				diaSemanaGregorianCalendar);
		calendarioInicio.set(GregorianCalendar.HOUR_OF_DAY, 14);
		calendarioInicio.set(GregorianCalendar.MINUTE, 0);

		GregorianCalendar calendarioFim = (GregorianCalendar) GregorianCalendar
				.getInstance();
		calendarioFim.set(GregorianCalendar.DAY_OF_WEEK,
				diaSemanaGregorianCalendar);
		calendarioFim.set(GregorianCalendar.HOUR_OF_DAY, 18);
		calendarioInicio.set(GregorianCalendar.MINUTE, 0);

		eventModel.addEvent(new DefaultScheduleEvent(descricao,
				calendarioInicio.getTime(), calendarioFim.getTime()));
		System.out.println(calendarioInicio.getTime());
		System.out.println(calendarioFim.getTime());
	}

	public Date getInitialDate() {

		adicionarEvento("PWEB - Daniel - TINF", GregorianCalendar.MONDAY);

		GregorianCalendar calendario = (GregorianCalendar) GregorianCalendar
				.getInstance();
		// calendario.set(GregorianCalendar.DAY_OF_WEEK,
		// GregorianCalendar.MONDAY);
		// calendario.set(GregorianCalendar.DAY_OF_MONTH, 18);
		// calendario.set(GregorianCalendar.MONTH, GregorianCalendar.NOVEMBER);
		// calendario.set(GregorianCalendar.YEAR, 2013);
		System.out.println(calendario.getTime());
		return calendario.getTime();
	}

	public void Ensalar() {
		controleEnsalamento.Ensalar();
		controleEnsalamento.Persistir();
		
		
	}
	
	public Collection<SelectItem> getValuesComboBoxProfessor() {
		   
        //SelectItem si = new SelectItem();
		ControleEnsalamento controleEnsalamento = new ControleEnsalamento();
        ArrayList<Professor> professores = controleEnsalamento.ConsultarByProfessor();
        Collection<SelectItem> listaComboBox = new ArrayList<SelectItem>();
       
        for (Professor professor : professores) {
            listaComboBox.add(new SelectItem(professor.getId(), professor.getNome()));
        }
       
        
        return listaComboBox;
    }
	public Collection<SelectItem> getValuesComboBoxTurma() {
		
		//SelectItem si = new SelectItem();
		ControleEnsalamento controleEnsalamento = new ControleEnsalamento();
		ArrayList<Turma> turmas = controleEnsalamento.ConsultarByTurma();
		Collection<SelectItem> listaComboBox = new ArrayList<SelectItem>();
		
		for (Turma turma : turmas) {
			listaComboBox.add(new SelectItem(turma.getId(), turma.getNome()));
		}
		
		return listaComboBox;
	}
	public Collection<SelectItem> getValuesComboBoxSala() {
		
		//SelectItem si = new SelectItem();
		ControleEnsalamento controleEnsalamento = new ControleEnsalamento();
		ArrayList<Sala> salas = controleEnsalamento.ConsultarBySala();
		Collection<SelectItem> listaComboBox = new ArrayList<SelectItem>();
		
		for (Sala sala : salas) {
			listaComboBox.add(new SelectItem(sala.getId(), sala.getNome()));
		}
		
		return listaComboBox;
	}
	public Collection<SelectItem> getValuesComboBoxCurso() {
		
		//SelectItem si = new SelectItem();
		ControleEnsalamento controleEnsalamento = new ControleEnsalamento();
		ArrayList<Curso> cursos = controleEnsalamento.ConsultarByCurso();
		Collection<SelectItem> listaComboBox = new ArrayList<SelectItem>();
		
		for (Curso curso : cursos) {
			listaComboBox.add(new SelectItem(curso.getId(), curso.getNome()));
		}
		
		return listaComboBox;
	}

	public void consultarByProfessor(){
		controleEnsalamento.ConsultarByProfessor();
	}
	
	public void consultarByProfessor(int id){
		controleEnsalamento.ConsultarByProfessor(id);
	}


	public ArrayList<Aula> getEnsalamento() {
		return controleEnsalamento.getAulas();
	}

	public ScheduleModel getEventModel() {
		return eventModel;
	}

	public void adicionarAulas() {
		Ensalar();
		ArrayList<Aula> array = getEnsalamento();
		
		for (int i = 0; i < array.size(); i++) {
			String descricao = array.get(i).texto2();
			int diaSemanaGregorianCalendar=0;
			if (array.get(i).getDia().getNome() == "seg") {
				 diaSemanaGregorianCalendar = GregorianCalendar.MONDAY;
			}
			if (array.get(i).getDia().getNome() == "ter") {
				 diaSemanaGregorianCalendar = GregorianCalendar.TUESDAY;
			}
			if (array.get(i).getDia().getNome() == "qua") {
				 diaSemanaGregorianCalendar = GregorianCalendar.WEDNESDAY;
			}
			if (array.get(i).getDia().getNome() == "qui") {
				 diaSemanaGregorianCalendar = GregorianCalendar.THURSDAY;
			}
			if (array.get(i).getDia().getNome() == "sex") {
				 diaSemanaGregorianCalendar = GregorianCalendar.FRIDAY;
			}

			adicionarEvento(descricao, diaSemanaGregorianCalendar);
		}
	}

}