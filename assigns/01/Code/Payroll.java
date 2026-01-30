
public class Payroll {
    public static final int INITIAL_MAXIMUM_SIZE = 1024;

    public Payroll() {
	people= new Employee[INITIAL_MAXIMUM_SIZE];   
    maximum_size = INITIAL_MAXIMUM_SIZE;  
    current_size=0;
    } 
    public int size() { 
        return current_size; 
    } 

    public void print() { 
        for (int i = 0 ; i < current_size; i++) { 
            Employee e = people[i]; 
            System.out.println(e.name + " " + e.ID + " " + e.salary); 
        } 
    

    }
    
    public void add_employee(Employee newbie) {
	    if (current_size == maximum_size) { 
            double_size(); 
        } 
        people[current_size] = newbie; 
        current_size++; 
    } 


    public void remove_employee(int i) throws EmployeeIndexException {
	    if (i<0 || i >= current_size) { 
            throw new EmployeeIndexException(); 
        } 
        people[i] = people[current_size - 1];  
        people[current_size - 1] = null; 
        current_size--; 


    }
    
    public int find_employee(String name) throws EmployeeNotFoundException {
        for (int i =0 ; i < current_size; i++) { 
            if (people[i].name.equals(name)) { 
                return i; 
            } 
        } 
        throw new EmployeeNotFoundException(); 

    }

    public void add_payroll(Payroll source) {
        for (int i = 0; i < source.current_size; i++) { 
            add_employee(source.people[i]);
        } 
            
    }

    public void copy_payroll(Payroll source) {
        people = new Employee[INITIAL_MAXIMUM_SIZE]; 
        maximum_size = INITIAL_MAXIMUM_SIZE; 
        current_size= source.current_size; 
        for (int i = 0; i <current_size; i++) { 
            people[i] = source.people[i]; 
        }

    } 

    private void double_size () {
        Employee[] big = new Employee[maximum_size*2]; 
        for(int i = 0; i <current_size; i++) { 
            big[i] = people[i]; 
        } 
        people = big; 
        maximum_size *= 2; 

    } 

    private Employee people[];
    private int maximum_size;
    private int current_size;
   
}
