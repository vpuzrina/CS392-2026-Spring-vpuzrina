
public class TestPayroll {
    public static void main(String[] args) { 

        Payroll p = new Payroll(); 
        Employee e1 = new Employee(); 
        e1.name = "Varvara"; 
        e1.ID = 1; 
        e1.salary = 1000; 

        Employee e2 = new Employee(); 
        e2.name = "Andrey"; 
        e2.ID = 2; 
        e2.salary = 2500; 
        p.add_employee(e1);
        p.add_employee(e2);

        p.print(); 

        try { 
            int i = p.find_employee("Varvara"); 
            p.remove_employee(i);
        } catch (Exception e) { 
            System.out.println("Exception caught");
        } 

        System.out.println("Finished"); 
    } 
}
