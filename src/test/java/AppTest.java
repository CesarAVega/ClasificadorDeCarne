/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import entities.*;
import static entities.clasificadordecarnederes.ClasificadorDeCarneDeRes.pdouConComa;
import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.*;
import services.*;
import static services.ServicesFacade.pdou;
import static services.ServicesFacade.pint;


public class AppTest {
    
    public AppTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void clearDB() throws SQLException {
        // establece conexión con una base de datos volátil solo se crea mientras se ejecutan las pruebas
        try (Connection conn = DriverManager.getConnection("jdbc:h2:file:./target/db/testdb;MODE=MYSQL", "sa", "")) {
            Statement stmt = conn.createStatement();
            stmt.execute("delete from localidad");
            stmt.execute("delete from grupo_racial");
            stmt.execute("delete from sistema");
            stmt.execute("delete from animal");
            stmt.execute("delete from tipo");
            stmt.execute("delete from carne");
            stmt.execute("delete from calidad");
            stmt.execute("delete from atributos_carne");
            stmt.execute("delete from calidad_atributos_carne");
            conn.commit();
        }
    }
    
    /**
     * Comprobación de carga de la base de datos
     */
    @Test
    public void cargarDB(){
        try {
            clearDB();
            Connection conn = DriverManager.getConnection("jdbc:h2:file:./target/db/testdb;MODE=MYSQL", "sa", "");
            
            Statement stmt = conn.createStatement();
            
            stmt.execute("INSERT INTO localidad (id, nombre) "
                    + "VALUES (1, 'PLATON')");
            stmt.execute("INSERT INTO localidad (id, nombre) "
                    + "VALUES (2, 'TEMPOAL')");
            stmt.execute("INSERT INTO localidad (id, nombre) "
                    + "VALUES (3, 'TANTOYUCA')");
            stmt.execute("INSERT INTO localidad (id, nombre) "
                    + "VALUES (4, 'EL HIGO')");
            
            stmt.execute("INSERT INTO grupo_racial (id, nombre) "
                    + "VALUES (1, 'CEBU')");
            stmt.execute("INSERT INTO grupo_racial (id, nombre) "
                    + "VALUES (2, 'CEBU Y SUIZO')");
            stmt.execute("INSERT INTO grupo_racial (id, nombre) "
                    + "VALUES (3, 'CEBU, SUIZO Y SIMENTAL')");
            stmt.execute("INSERT INTO grupo_racial (id, nombre) "
                    + "VALUES (4, 'CEBU, SUIZO Y HOLTEINS')");
            
            stmt.execute("INSERT INTO tipo (id, nombre) "
                    + "VALUES (1, 'NOVILLA')");
            stmt.execute("INSERT INTO tipo (id, nombre) "
                    + "VALUES (2, 'VACA')");
            stmt.execute("INSERT INTO tipo (id, nombre) "
                    + "VALUES (3, 'TORETE')");
            
            stmt.execute("INSERT INTO sistema (id, nombre) "
                    + "VALUES (1, 'A.C')");
            stmt.execute("INSERT INTO sistema (id, nombre) "
                    + "VALUES (2, 'A.P')");
            
            stmt.execute("INSERT INTO calidad (id, descripcion) "
                    + "VALUES (1, 'SUPERIOR')");
            stmt.execute("INSERT INTO calidad (id, descripcion) "
                    + "VALUES (2, 'SELECCIONADA')");
            stmt.execute("INSERT INTO calidad (id, descripcion) "
                    + "VALUES (3, 'ESTANDAR')");
            stmt.execute("INSERT INTO calidad (id, descripcion) "
                    + "VALUES (4, 'COMERCIAL')");
            
            stmt.execute("INSERT INTO atributos_carne (id, descripcion) "
                    + "VALUES (1, 'Conformación de la canal')");
            stmt.execute("INSERT INTO atributos_carne (id, descripcion) "
                    + "VALUES (2, 'Distribucion de grasa subcútane')");
            stmt.execute("INSERT INTO atributos_carne (id, descripcion) "
                    + "VALUES (3, 'Cobertura de grasa perirrenal')");
            stmt.execute("INSERT INTO atributos_carne (id, descripcion) "
                    + "VALUES (4, 'Color de la grasa')");
            stmt.execute("INSERT INTO atributos_carne (id, descripcion) "
                    + "VALUES (5, 'Color de la carne')");
            
            stmt.execute("INSERT INTO carne (id, canal_frio_der, canal_frio_izq, ojo_chuleta, grosor_grasa_dorsal, calidad_id) "
                    + "VALUES (1, '95.20', '93.7', '81.94', '0.39', 2)");
            
            stmt.execute("INSERT INTO animal (id, localidad_id, grupo_racial_id, tipo_id, sistema_id, edad, kpv, carne_id) "
                    + "VALUES (1, 1, 4, 1, 1, 19, 545, 1)");
            
            stmt.execute("INSERT INTO calidad_atributos_carne (carne_id, calidad_id, atributos_carne_id) "
                    + "VALUES (1, 3, 1)");
            stmt.execute("INSERT INTO calidad_atributos_carne (carne_id, calidad_id, atributos_carne_id) "
                    + "VALUES (1, 3, 2)");
            stmt.execute("INSERT INTO calidad_atributos_carne (carne_id, calidad_id, atributos_carne_id) "
                    + "VALUES (1, 1, 3)");
            stmt.execute("INSERT INTO calidad_atributos_carne (carne_id, calidad_id, atributos_carne_id) "
                    + "VALUES (1, 1, 4)");
            stmt.execute("INSERT INTO calidad_atributos_carne (carne_id, calidad_id, atributos_carne_id) "
                    + "VALUES (1, 3, 5)");
            
            
            stmt.execute("INSERT INTO carne (id, canal_frio_der, canal_frio_izq, ojo_chuleta, grosor_grasa_dorsal, calidad_id) "
                    + "VALUES (2, '99.30', '100.00', '63.87', '0.29', 2)");
            
            stmt.execute("INSERT INTO animal (id, localidad_id, grupo_racial_id, tipo_id, sistema_id, edad, kpv, carne_id) "
                    + "VALUES (2, 1, 2, 1, 1, 18, 303, 2)");
            
            stmt.execute("INSERT INTO calidad_atributos_carne (carne_id, calidad_id, atributos_carne_id) "
                    + "VALUES (2, 3, 1)");
            stmt.execute("INSERT INTO calidad_atributos_carne (carne_id, calidad_id, atributos_carne_id) "
                    + "VALUES (2, 3, 2)");
            stmt.execute("INSERT INTO calidad_atributos_carne (carne_id, calidad_id, atributos_carne_id) "
                    + "VALUES (2, 1, 3)");
            stmt.execute("INSERT INTO calidad_atributos_carne (carne_id, calidad_id, atributos_carne_id) "
                    + "VALUES (2, 1, 4)");
            stmt.execute("INSERT INTO calidad_atributos_carne (carne_id, calidad_id, atributos_carne_id) "
                    + "VALUES (2, 3, 5)");
            
            ServicesFacade sf = ServicesFacade.getInstance("h2-applicationconfig.properties");
            Set<Animal> ans = sf.getData();
            
            Iterator<Animal> i = ans.iterator();
            
            int[] prueba = new int[]{3,3,1,1,3};
            
            while(i.hasNext()){
                Animal animal = i.next();
                if(animal.getID() == 1){
                    Assert.assertTrue("Animal no valido ",animal.getID() == 1);
                    Assert.assertTrue("Localida no valida", animal.getLocalidad().getKey() == 1 && animal.getLocalidad().getDescripcion().equals("PLATON"));
                    Assert.assertTrue("Grupo racial no valido", animal.getGrupoRacial().getKey() == 4 && animal.getGrupoRacial().getDescripcion().equals("CEBU, SUIZO Y HOLTEINS"));
                    Assert.assertTrue("Tipo no valido", animal.getTipo().getKey() == 1 && animal.getTipo().getDescripcion().equals("NOVILLA"));
                    Assert.assertTrue("Sistema no valido", animal.getSistema().getKey() == 1 && animal.getSistema().getDescripcion().equals("A.C"));
                    Assert.assertTrue("Edad no valido", animal.getEdad() == 19);
                    Assert.assertTrue("KPV no valido", animal.getKpv() == 545);
                    
                    Carne carne = animal.getCarne();
                    Assert.assertTrue("canal_frio_der no valido ",carne.getPesoCanalFrioDer() == pdou("95.20"));
                    Assert.assertTrue("canal_frio_izq no valido ",carne.getPesoCanalFrioIzq()== pdou("93.7"));
                    Assert.assertTrue("ojo_chuleta no valido ",carne.getOjoDeLaChuleta()== pdou("81.94"));
                    Assert.assertTrue("grosor_grasa_dorsal no valido ",carne.getGrosorDeGrasaDorsal()== pdou("0.39"));
                    Assert.assertTrue("calidad_id no valido ",carne.getCalidad().getKey() == 2 && carne.getCalidad().getDescripcion().equals("SELECCIONADA"));
                                        
                    for ( int j = 0; j < prueba.length; j++){                        
                        Assert.assertTrue("Atributo "+j+" no valido",animal.getCarne().getCalidades().get(j).getCalidad().getKey() == prueba[j]);
                    }
                }else{
                    Assert.assertTrue("Animal no valido ",animal.getID() == 2);
                    Assert.assertTrue("Localida no valida", animal.getLocalidad().getKey() == 1 && animal.getLocalidad().getDescripcion().equals("PLATON"));
                    Assert.assertTrue("Grupo racial no valido", animal.getGrupoRacial().getKey() == 2 && animal.getGrupoRacial().getDescripcion().equals("CEBU Y SUIZO"));
                    Assert.assertTrue("Tipo no valido", animal.getTipo().getKey() == 1 && animal.getTipo().getDescripcion().equals("NOVILLA"));
                    Assert.assertTrue("Sistema no valido", animal.getSistema().getKey() == 1 && animal.getSistema().getDescripcion().equals("A.C"));
                    Assert.assertTrue("Edad no valido", animal.getEdad() == 18);
                    Assert.assertTrue("KPV no valido", animal.getKpv() == 303);
                    
                    Carne carne = animal.getCarne();
                    Assert.assertTrue("canal_frio_der no valido ",carne.getPesoCanalFrioDer() == pdou("99.30"));
                    Assert.assertTrue("canal_frio_izq no valido ",carne.getPesoCanalFrioIzq()== pdou("100.00"));
                    Assert.assertTrue("ojo_chuleta no valido ",carne.getOjoDeLaChuleta()== pdou("63.87"));
                    Assert.assertTrue("grosor_grasa_dorsal no valido ",carne.getGrosorDeGrasaDorsal()== pdou("0.29")); 
                    Assert.assertTrue("calidad_id no valido ",carne.getCalidad().getKey() == 2 && carne.getCalidad().getDescripcion().equals("SELECCIONADA"));
                
                    for ( int j = 0; j < prueba.length; j++){                        
                        Assert.assertTrue("Atributo "+j+" no valido",animal.getCarne().getCalidades().get(j).getCalidad().getKey() == prueba[j]);
                    }
                }
                
            }
        } catch (SQLException ex) {           
            Logger.getLogger(AppTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Cargar la red actual para dar respuesta a una consulta
     * @throws java.sql.SQLException
     */
    @Test
    public void cargarRedNeuronalDarValor1Dato(){
        try {
            clearDB();
            ServicesFacade sf = ServicesFacade.getInstance("h2-applicationconfig.properties");
            ArrayList<AtributoCarne> calidades;
            Carne carne;
            Animal animal, ans;
            //SELECCIONADA
            calidades = new ArrayList<>();
            calidades.add(new AtributoCarne(1, "Conformación de la canal", new Calidad(3, "ESTANDAR")));
            calidades.add(new AtributoCarne(2, "Distribucion de grasa subcútane", new Calidad(3, "ESTANDAR")));
            calidades.add(new AtributoCarne(3, "Cobertura de grasa perirrenal", new Calidad(1, "SUPERIOR")));
            calidades.add(new AtributoCarne(4, "Color de la grasa", new Calidad(1, "SUPERIOR")));
            calidades.add(new AtributoCarne(5, "Color de la carne", new Calidad(3, "ESTANDAR")));
            carne = new Carne(pdou("95.20"), pdou("93.7"),
                    pdou("81.94"), pdou("0.39"), calidades,
                    null);
            animal = new Animal(1, new Tipo(1, "NOVILLA"), 19, 545,
                    new Localidad(1, "PLATON"), carne,
                    new GrupoRacial(4, "CEBU, SUIZO Y HOLTEINS"), new Sistema(1, "A.C"));
            ans = sf.valueOf(animal); // contien respuesta de la clasificación
            Assert.assertTrue("Valor SELECCIONADA no valido ", ans.getCarne().getCalidad().getDescripcion().equals("SELECCIONADA"));
            //SUPERIOR
            calidades = new ArrayList<>();
            calidades.add(new AtributoCarne(1, "Conformación de la canal", new Calidad(3, "ESTANDAR")));
            calidades.add(new AtributoCarne(2, "Distribucion de grasa subcútane", new Calidad(3, "ESTANDAR")));
            calidades.add(new AtributoCarne(3, "Cobertura de grasa perirrenal", new Calidad(1, "SUPERIOR")));
            calidades.add(new AtributoCarne(4, "Color de la grasa", new Calidad(1, "SUPERIOR")));
            calidades.add(new AtributoCarne(5, "Color de la carne", new Calidad(1, "SUPERIOR")));
            carne = new Carne(pdou("81.20"), pdou("80.40"),
                    pdou("63.87"), pdou("0.49"), calidades,
                    null);
            animal = new Animal(1, new Tipo(1, "NOVILLA"), 20, 412,
                    new Localidad(1, "PLATON"), carne,
                    new GrupoRacial(2, "CEBU Y SUIZO"), new Sistema(1, "A.C"));
            ans = sf.valueOf(animal); // contiene respuesta de la clasificación
            Assert.assertTrue("Valor SUPERIOR no valido ", ans.getCarne().getCalidad().getDescripcion().equals("SUPERIOR"));
            //ESTANDAR
            calidades = new ArrayList<>();
            calidades.add(new AtributoCarne(1, "Conformación de la canal", new Calidad(3, "ESTANDAR")));
            calidades.add(new AtributoCarne(2, "Distribucion de grasa subcútane", new Calidad(4, "COMERCIAL")));
            calidades.add(new AtributoCarne(3, "Cobertura de grasa perirrenal", new Calidad(1, "SUPERIOR")));
            calidades.add(new AtributoCarne(4, "Color de la grasa", new Calidad(1, "SUPERIOR")));
            calidades.add(new AtributoCarne(5, "Color de la carne", new Calidad(4, "COMERCIAL")));
            carne = new Carne(pdou("110.30"), pdou("110.40"),
                    pdou("61.29"), pdou("0.19"), calidades,
                    null);
            animal = new Animal(1, new Tipo(1, "NOVILLA"), 20, 548,
                    new Localidad(1, "PLATON"), carne,
                    new GrupoRacial(2, "CEBU Y SUIZO"), new Sistema(1, "A.C"));
            ans = sf.valueOf(animal); // contiene respuesta de la clasificación
            Assert.assertTrue("Valor ESTANDAR no valido ", ans.getCarne().getCalidad().getDescripcion().equals("ESTANDAR"));
        } catch (SQLException ex) {
            Logger.getLogger(AppTest.class.getName()).log(Level.SEVERE, null, ex);
        }        
        
    }
        
    /**
     * Prueba de lectura de un archivo, y se prueban las funciones para traer descripciones de la base de datos
     * como por ejemplo, tipo, grupo racial entre otros con el fin de crear los objetos
     */
    @Test
    public void enviarArchivoYObtenerCalidades(){
        try {
            clearDB();
            //Llenar la base de datos
            Connection conn = DriverManager.getConnection("jdbc:h2:file:./target/db/testdb;MODE=MYSQL", "sa", "");
            
            Statement stmt = conn.createStatement();
            
            stmt.execute("INSERT INTO localidad (id, nombre) "
                    + "VALUES (1, 'PLATON')");
            stmt.execute("INSERT INTO localidad (id, nombre) "
                    + "VALUES (2, 'TEMPOAL')");
            stmt.execute("INSERT INTO localidad (id, nombre) "
                    + "VALUES (3, 'TANTOYUCA')");
            stmt.execute("INSERT INTO localidad (id, nombre) "
                    + "VALUES (4, 'EL HIGO')");
            
            stmt.execute("INSERT INTO grupo_racial (id, nombre) "
                    + "VALUES (1, 'CEBU')");
            stmt.execute("INSERT INTO grupo_racial (id, nombre) "
                    + "VALUES (2, 'CEBU Y SUIZO')");
            stmt.execute("INSERT INTO grupo_racial (id, nombre) "
                    + "VALUES (3, 'CEBU, SUIZO Y SIMENTAL')");
            stmt.execute("INSERT INTO grupo_racial (id, nombre) "
                    + "VALUES (4, 'CEBU, SUIZO Y HOLTEINS')");
            
            stmt.execute("INSERT INTO tipo (id, nombre) "
                    + "VALUES (1, 'NOVILLA')");
            stmt.execute("INSERT INTO tipo (id, nombre) "
                    + "VALUES (2, 'VACA')");
            stmt.execute("INSERT INTO tipo (id, nombre) "
                    + "VALUES (3, 'TORETE')");
            
            stmt.execute("INSERT INTO sistema (id, nombre) "
                    + "VALUES (1, 'A.C')");
            stmt.execute("INSERT INTO sistema (id, nombre) "
                    + "VALUES (2, 'A.P')");
            
            stmt.execute("INSERT INTO calidad (id, descripcion) "
                    + "VALUES (1, 'SUPERIOR')");
            stmt.execute("INSERT INTO calidad (id, descripcion) "
                    + "VALUES (2, 'SELECCIONADA')");
            stmt.execute("INSERT INTO calidad (id, descripcion) "
                    + "VALUES (3, 'ESTANDAR')");
            stmt.execute("INSERT INTO calidad (id, descripcion) "
                    + "VALUES (4, 'COMERCIAL')");
            
            stmt.execute("INSERT INTO atributos_carne (id, descripcion) "
                    + "VALUES (1, 'Conformación de la canal')");
            stmt.execute("INSERT INTO atributos_carne (id, descripcion) "
                    + "VALUES (2, 'Distribucion de grasa subcútane')");
            stmt.execute("INSERT INTO atributos_carne (id, descripcion) "
                    + "VALUES (3, 'Cobertura de grasa perirrenal')");
            stmt.execute("INSERT INTO atributos_carne (id, descripcion) "
                    + "VALUES (4, 'Color de la grasa')");
            stmt.execute("INSERT INTO atributos_carne (id, descripcion) "
                    + "VALUES (5, 'Color de la carne')");
            
            // Sección de ServiceFacade
            ServicesFacade sf = ServicesFacade.getInstance("h2-applicationconfig.properties");
            
            //Variables a usar
            ArrayList<AtributoCarne> calidades; // Atributos adicionales de la carne
            Carne carne;
            Animal animal;
            Set<Animal> data = new LinkedHashSet<>(); // contiene los animales del archivo de datos enviados
            ArrayList<String> dataSolve = new ArrayList<>(); // contiene en orden las calidades de entrada para posterior comparación
            Calidad calidad;
            //Lectura de archivo
            BufferedReader in = new BufferedReader(new FileReader("base de datos calidad de la canal.txt")); 
            String[] tok; 
            int key = 1; // identificador para cada animal
            while (in.ready()){
                tok = in.readLine().split("\\s+"); //Lectura de linea y separada por espacio
                //System.out.println(key+" "+Arrays.toString(tok));
                
                // creando las propiedades de los atributos adicionales para cada carne
                calidades = new ArrayList<>();
                for (int i = 1; i < 6; i++){
                    calidad = sf.getCalidad(pint(tok[5+i]));
                    calidades.add(new AtributoCarne(i, sf.detDescripcioAtributo(i), calidad));                    
                }
                
                // crear carne se usa el metodo en ClasificadorDeCarneDeRes para convertir un String con coma a double
                carne = new Carne(pdouConComa(tok[11]), pdouConComa(tok[12]),
                        pdouConComa(tok[13]), pdouConComa(tok[14]), calidades,
                        sf.getCalidad(pint(tok[15])));
                
                // se incrementa key
                animal = new Animal(key++, sf.getTipo(pint(tok[2])) , pint(tok[4]), pint(tok[5]),
                        sf.getLocalidad(pint(tok[0])), carne,
                        sf.getGrupoRacial(pint(tok[1])), sf.getSistema(pint(tok[3])));
                /**
                System.out.println(animal.getID()+" "
                        + ""+animal.getLocalidad().getKey()+" "
                        + ""+animal.getGrupoRacial().getKey()+" "
                        + ""+animal.getTipo().getKey()+" "
                        + ""+animal.getSistema().getKey()+" "
                        + ""+animal.getEdad()+" "
                        + ""+animal.getKpv()+" "
                        + ""+animal.getCarne().getCalidades().get(0).getCalidad().getKey()+" "
                        + ""+animal.getCarne().getCalidades().get(1).getCalidad().getKey()+" "
                        + ""+animal.getCarne().getCalidades().get(2).getCalidad().getKey()+" "
                        + ""+animal.getCarne().getCalidades().get(3).getCalidad().getKey()+" "
                        + ""+animal.getCarne().getCalidades().get(4).getCalidad().getKey()+" "
                        + ""+animal.getCarne().getPesoCanalFrioDer()+" "
                        + ""+animal.getCarne().getPesoCanalFrioIzq()+" "
                        + ""+animal.getCarne().getOjoDeLaChuleta()+" "
                        + ""+animal.getCarne().getGrosorDeGrasaDorsal()+" "
                        + ""+animal.getCarne().getCalidad().getKey());
                   */     
                data.add(animal); // set de datos convertidos a objetos animal
                dataSolve.add(animal.getCarne().getCalidad().getDescripcion()); // almacena en orden las calidades
            }
            sf.valueOfall(data); // modifica las calidades de la carne de cada animal
            key = 0; // index para el arreglo de calidades de comparación
            int cont = 0; //contador de colisiones correctas           
            for(Animal a: data){
                if (a.getID() == key+1 && 
                        a.getCarne().getCalidad().getDescripcion().equals(dataSolve.get(key))
                    ){
                    cont++; // si las calidades son iguales contador para posterior uso comparativo de eficiencia
                }
                /**
                else{
               
                System.out.println(a.getID()+" "
                        + ""+a.getCarne().getCalidad().getDescripcion()+" "
                        + ""+dataSolve.get(key));
                }*/
                key++;
            }                      
            // Se compara con la efectividad de la Red Neuronal Base -1 para efectos de errores
            Assert.assertTrue("NO Funciona la Red Neuronal", ((double)cont/(double)key)*100 >= sf.getNnet().getPorcentajeEfectividad()-1);
        } catch (SQLException | RuntimeException | IOException ex) {
            Logger.getLogger(AppTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
