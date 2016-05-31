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
            //Lectura de archivo
            BufferedReader in = new BufferedReader(new FileReader("base de datos calidad de la canal.txt")); 
            String[] tok; double[] temp; 
            int key = 1; // identificador para cada animal
            Set<double[]> doubles = new LinkedHashSet<>();
            ArrayList<String> dataSolve = new ArrayList<>();
            while (in.ready()){
                tok = in.readLine().split("\\s+"); //Lectura de linea y separada por espacio
                temp = new double[tok.length];
                //System.out.println(key+" "+Arrays.toString(tok));
                for (int i = 0; i < tok.length; i++){                    
                    if (11 <= i && i <=14){
                        temp[i] = pdouConComa(tok[i]);
                    }else{
                        temp[i] = Double.parseDouble(tok[i]);
                    }
                }
                dataSolve.add(tok[tok.length-1]);
                doubles.add(temp);
            }
            Set<Animal> data = sf.vectorToAnimalAll(doubles);
            
            sf.valueOfall(data); // modifica las calidades de la carne de cada animal
            key = 0; // index para el arreglo de calidades de comparación
            int cont = 0; //contador de colisiones correctas           
            for(Animal a: data){                
                if (a.getID() == key+1 && 
                        a.getCarne().getCalidad().getKey() == Integer.parseInt(dataSolve.get(key))
                        ){
                    cont++; // si las calidades son iguales contador para posterior uso comparativo de eficiencia
                }
                key++;
            }                      
            // Se compara con la efectividad de la Red Neuronal Base -1 para efectos de errores
            Assert.assertTrue("NO Funciona la Red Neuronal", ((double)cont/(double)key)*100 >= sf.getNnet().getPorcentajeEfectividad()-1);
        } catch (SQLException | RuntimeException | IOException ex) {
            Logger.getLogger(AppTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    /**
     * Verifica que la informacion de la base de datos sea traida con exito
     */
    @Test
    public void getTodosCalidad(){
    
        try {
            clearDB();
            Connection conn = DriverManager.getConnection("jdbc:h2:file:./target/db/testdb;MODE=MYSQL", "sa", "");
            
            Statement stmt = conn.createStatement();
            stmt.execute("INSERT INTO calidad (id, descripcion) "
                    + "VALUES (1, 'SUPERIOR')");
            stmt.execute("INSERT INTO calidad (id, descripcion) "
                    + "VALUES (2, 'SELECCIONADA')");
            stmt.execute("INSERT INTO calidad (id, descripcion) "
                    + "VALUES (3, 'ESTANDAR')");
            stmt.execute("INSERT INTO calidad (id, descripcion) "
                    + "VALUES (4, 'COMERCIAL')");
            
            ArrayList<Calidad> solveCalidad = new ArrayList<>();
            solveCalidad.add(new Calidad(1, "SUPERIOR"));
            solveCalidad.add(new Calidad(2, "SELECCIONADA"));
            solveCalidad.add(new Calidad(3, "ESTANDAR"));
            solveCalidad.add(new Calidad(4, "COMERCIAL"));
                                    
            ServicesFacade sf = ServicesFacade.getInstance("h2-applicationconfig.properties");
            
            ArrayList<Calidad> calidades = sf.getTodosCalidad();
            
            for (int i = 0; i < calidades.size(); i++){
                Assert.assertTrue("NO obtiene los datos en orden", 
                        solveCalidad.get(i).getKey() == calidades.get(i).getKey() 
                        && solveCalidad.get(i).getDescripcion().equals(calidades.get(i).getDescripcion()));    
            }
        } catch (SQLException ex) {
            Logger.getLogger(AppTest.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
    
    
    /**
     * Verifica que la informacion de la base de datos sea traida con exito
     */
    @Test
    public void getTodosLocalidad(){
    
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
            
            ArrayList<Localidad> solveLocalidad = new ArrayList<>();
            solveLocalidad.add(new Localidad(1, "PLATON"));
            solveLocalidad.add(new Localidad(2, "TEMPOAL"));
            solveLocalidad.add(new Localidad(3, "TANTOYUCA"));
            solveLocalidad.add(new Localidad(4, "EL HIGO"));
                                    
            ServicesFacade sf = ServicesFacade.getInstance("h2-applicationconfig.properties");
            
            ArrayList<Localidad> localidades = sf.getTodosLocalidad();
            
            for (int i = 0; i < localidades.size(); i++){
                Assert.assertTrue("NO obtiene los datos en orden", 
                        solveLocalidad.get(i).getKey() == localidades.get(i).getKey() 
                        && solveLocalidad.get(i).getDescripcion().equals(localidades.get(i).getDescripcion()));    
            }
        } catch (SQLException ex) {
            Logger.getLogger(AppTest.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    
    /**
     * Verifica que la informacion de la base de datos sea traida con exito
     */
    @Test
    public void getTodosGrupoRacial(){
    
        try {
            clearDB();
            Connection conn = DriverManager.getConnection("jdbc:h2:file:./target/db/testdb;MODE=MYSQL", "sa", "");
            
            Statement stmt = conn.createStatement();

            stmt.execute("INSERT INTO grupo_racial (id, nombre) "
                    + "VALUES (1, 'CEBU')");
            stmt.execute("INSERT INTO grupo_racial (id, nombre) "
                    + "VALUES (2, 'CEBU Y SUIZO')");
            stmt.execute("INSERT INTO grupo_racial (id, nombre) "
                    + "VALUES (3, 'CEBU, SUIZO Y SIMENTAL')");
            stmt.execute("INSERT INTO grupo_racial (id, nombre) "
                    + "VALUES (4, 'CEBU, SUIZO Y HOLTEINS')");
            
            ArrayList<GrupoRacial> solveGrupoRacial = new ArrayList<>();
            solveGrupoRacial.add(new GrupoRacial(1, "CEBU"));
            solveGrupoRacial.add(new GrupoRacial(2, "CEBU Y SUIZO"));
            solveGrupoRacial.add(new GrupoRacial(3, "CEBU, SUIZO Y SIMENTAL"));
            solveGrupoRacial.add(new GrupoRacial(4, "CEBU, SUIZO Y HOLTEINS"));
                                    
            ServicesFacade sf = ServicesFacade.getInstance("h2-applicationconfig.properties");
            
            ArrayList<GrupoRacial> gruposRaciales = sf.getTodosGrupoRacial();
            
            for (int i = 0; i < gruposRaciales.size(); i++){
                Assert.assertTrue("NO obtiene los datos en orden", 
                        solveGrupoRacial.get(i).getKey() == gruposRaciales.get(i).getKey() 
                        && solveGrupoRacial.get(i).getDescripcion().equals(gruposRaciales.get(i).getDescripcion()));    
            }
        } catch (SQLException ex) {
            Logger.getLogger(AppTest.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    
        /**
     * Verifica que la informacion de la base de datos sea traida con exito
     */
    @Test
    public void getTodosTipo(){
    
        try {
            clearDB();
            Connection conn = DriverManager.getConnection("jdbc:h2:file:./target/db/testdb;MODE=MYSQL", "sa", "");
            
            Statement stmt = conn.createStatement();

            stmt.execute("INSERT INTO tipo (id, nombre) "
                    + "VALUES (1, 'NOVILLA')");
            stmt.execute("INSERT INTO tipo (id, nombre) "
                    + "VALUES (2, 'VACA')");
            stmt.execute("INSERT INTO tipo (id, nombre) "
                    + "VALUES (3, 'TORETE')");
            
            ArrayList<Tipo> solveTipo = new ArrayList<>();
            solveTipo.add(new Tipo(1, "NOVILLA"));
            solveTipo.add(new Tipo(2, "VACA"));
            solveTipo.add(new Tipo(3, "TORETE"));            
                                    
            ServicesFacade sf = ServicesFacade.getInstance("h2-applicationconfig.properties");
            
            ArrayList<Tipo> tipos = sf.getTodosTipo();
            
            for (int i = 0; i < tipos.size(); i++){
                Assert.assertTrue("NO obtiene los datos en orden", 
                        solveTipo.get(i).getKey() == tipos.get(i).getKey() 
                        && solveTipo.get(i).getDescripcion().equals(tipos.get(i).getDescripcion()));    
            }
        } catch (SQLException ex) {
            Logger.getLogger(AppTest.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    
        /**
     * Verifica que la informacion de la base de datos sea traida con exito
     */
    @Test
    public void getTodosSistemas(){
    
        try {
            clearDB();
            Connection conn = DriverManager.getConnection("jdbc:h2:file:./target/db/testdb;MODE=MYSQL", "sa", "");
            
            Statement stmt = conn.createStatement();
            
            stmt.execute("INSERT INTO sistema (id, nombre) "
                    + "VALUES (1, 'A.C')");
            stmt.execute("INSERT INTO sistema (id, nombre) "
                    + "VALUES (2, 'A.P')");
            
            ArrayList<Sistema> solveSistemas = new ArrayList<>();
            solveSistemas.add(new Sistema(1, "A.C"));
            solveSistemas.add(new Sistema(2, "A.P"));
                                    
            ServicesFacade sf = ServicesFacade.getInstance("h2-applicationconfig.properties");
            
            ArrayList<Sistema> sistemas = sf.getTodosSistema();
            
            for (int i = 0; i < sistemas.size(); i++){
                Assert.assertTrue("NO obtiene los datos en orden", 
                        solveSistemas.get(i).getKey() == sistemas.get(i).getKey() 
                        && solveSistemas.get(i).getDescripcion().equals(sistemas.get(i).getDescripcion()));    
            }
        } catch (SQLException ex) {
            Logger.getLogger(AppTest.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    
    @Test
    public void insertLocalidad(){
        try {
            clearDB();
            ArrayList<Localidad> solveLocalidades = new ArrayList<>();
            solveLocalidades.add(new Localidad(1, "Tantoyuca"));
            solveLocalidades.add(new Localidad(2, "Colombia"));
            
            ServicesFacade sf = ServicesFacade.getInstance("h2-applicationconfig.properties");
            for (Localidad l : solveLocalidades){
                sf.insertLocalidad(l);
            }
            
            ArrayList<Localidad> localidades = sf.getTodosLocalidad();
            
            for (int i = 0; i < localidades.size(); i++){
                Assert.assertTrue("No ingreso el valor correcto",
                        solveLocalidades.get(i).getKey() == localidades.get(i).getKey()
                                && solveLocalidades.get(i).getDescripcion().equals(localidades.get(i).getDescripcion())
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(AppTest.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    @Test
    public void insertGrupoRacial(){
        try {
            clearDB();
            ArrayList<GrupoRacial> solveGrupoRacial = new ArrayList<>();
            solveGrupoRacial.add(new GrupoRacial(1, "Tantoyuquense"));
            solveGrupoRacial.add(new GrupoRacial(2, "Colombiano"));
            
            ServicesFacade sf = ServicesFacade.getInstance("h2-applicationconfig.properties");
            for (GrupoRacial grupoRacial : solveGrupoRacial){
                sf.insertGrupoRacial(grupoRacial);
            }
            
            ArrayList<GrupoRacial> gruposRaciales = sf.getTodosGrupoRacial();
            
            for (int i = 0; i < gruposRaciales.size(); i++){
                Assert.assertTrue("No ingreso el valor correcto",
                        solveGrupoRacial.get(i).getKey() == gruposRaciales.get(i).getKey()
                                && solveGrupoRacial.get(i).getDescripcion().equals(gruposRaciales.get(i).getDescripcion())
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(AppTest.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    @Test
    public void insertTipo(){
        try {
            clearDB();
            ArrayList<Tipo> solveTipo = new ArrayList<>();
            solveTipo.add(new Tipo(1, "CAVA"));
            solveTipo.add(new Tipo(2, "TOROTE"));
            
            ServicesFacade sf = ServicesFacade.getInstance("h2-applicationconfig.properties");
            for (Tipo tipo : solveTipo){
                sf.insertTipo(tipo);
            }
            
            ArrayList<Tipo> tipos = sf.getTodosTipo();
            
            for (int i = 0; i < tipos.size(); i++){
                Assert.assertTrue("No ingreso el valor correcto",
                        solveTipo.get(i).getKey() == tipos.get(i).getKey()
                                && solveTipo.get(i).getDescripcion().equals(tipos.get(i).getDescripcion())
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(AppTest.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    @Test
    public void insertSistema(){
        try {
            clearDB();
            ArrayList<Sistema> solveSistemas = new ArrayList<>();
            solveSistemas.add(new Sistema(1, "A.M"));
            solveSistemas.add(new Sistema(2, "A.T"));
            
            ServicesFacade sf = ServicesFacade.getInstance("h2-applicationconfig.properties");
            for (Sistema sistema : solveSistemas){
                sf.insertSistema(sistema);
            }
            
            ArrayList<Sistema> sistemas = sf.getTodosSistema();
            
            for (int i = 0; i < sistemas.size(); i++){
                Assert.assertTrue("No ingreso el valor correcto",
                        solveSistemas.get(i).getKey() == sistemas.get(i).getKey()
                                && solveSistemas.get(i).getDescripcion().equals(sistemas.get(i).getDescripcion())
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(AppTest.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    @Test
    public void insertCalidad(){
        try {
            clearDB();
            ArrayList<Calidad> solveCalidad = new ArrayList<>();
            solveCalidad.add(new Calidad(1, "Muy Superior"));
            solveCalidad.add(new Calidad(2, "Super superior"));
            
            ServicesFacade sf = ServicesFacade.getInstance("h2-applicationconfig.properties");
            for (Calidad calidad : solveCalidad){
                sf.insertCalidad(calidad);
            }
            
            ArrayList<Calidad> calidades = sf.getTodosCalidad();
            
            for (int i = 0; i < calidades.size(); i++){
                Assert.assertTrue("No ingreso el valor correcto",
                        solveCalidad.get(i).getKey() == calidades.get(i).getKey()
                                && solveCalidad.get(i).getDescripcion().equals(calidades.get(i).getDescripcion())
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(AppTest.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
   
}
