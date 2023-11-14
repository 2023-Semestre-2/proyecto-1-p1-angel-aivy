package com.proyecto1.gestordeprocesos;

import com.proyecto1.gestordeprocesos.modelos.EntradaBCP;
import com.proyecto1.gestordeprocesos.modelos.EntradaDisco;
import com.proyecto1.gestordeprocesos.modelos.EntradaMemoria;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Controller {
    // Algoritmo de ejecución de CPU
    @FXML
    private ToggleGroup groupAlgoritmo = new ToggleGroup();
    @FXML
    private RadioButton rbFCFS;

    @FXML
    private RadioButton rbSRT;

    @FXML
    private RadioButton rbSJF;

    @FXML
    private RadioButton rbRR;

    @FXML
    private RadioButton rbHRRN;
    // Asignación de memoria
    @FXML
    private ToggleGroup groupAsignacion = new ToggleGroup();

    @FXML
    private RadioButton rbPaginacion;

    @FXML
    private RadioButton rbParticionamientoDinamico;

    @FXML
    private RadioButton rbParticionamientoFijo;

    // Cantidad de CPU
    @FXML
    private ToggleGroup groupCPU = new ToggleGroup();

    @FXML
    private RadioButton rbCPU1;

    @FXML
    private RadioButton rbCPU2;

    @FXML
    private RadioButton rbCPU3;

    @FXML
    private RadioButton rbCPU4;

    @FXML
    private Button cargarArchivos;
    @FXML
    private Button ejecutar;
    @FXML
    private Button siguiente;
    private FileManager fileManager;

    //Paginacion
    @FXML
    private TextField quantum;
    @FXML
    private TextField pagesSize;
    @FXML
    private TextField tablePagesSize;

    // Particionamiento dinamico
    @FXML
    private TextField initialSize;
    @FXML
    private ChoiceBox<String> politicaAsig;

    //Particionamiento fijo
    @FXML
    private TextField numeroParticiones;
    @FXML
    private TextField particionSize;

    //Tablas
    @FXML
    private TableView<EntradaDisco> tablaDisco;
    @FXML
    private TableColumn<EntradaDisco, Integer> columnaPos;
    @FXML
    private TableColumn<EntradaDisco, String> columnaValor;

    //Tabla memoria
    @FXML
    private TableView<EntradaMemoria> tablaMemoria;
    @FXML
    private TableColumn<EntradaMemoria, Integer> columnaPosMemoria;
    @FXML
    private TableColumn<EntradaMemoria, String> columnaValorMemoria;

    //Tabla CPU1
    @FXML
    private TableView<EntradaBCP> tablaCPU1;
    @FXML
    private TableColumn<EntradaBCP, Integer> pidColumn;
    @FXML
    private TableColumn<EntradaBCP, String> estadoColumn;
    @FXML
    private TableColumn<EntradaBCP, Integer> tiempoCPUColum;
    @FXML
    private TableColumn<EntradaBCP, Integer> pcColumn;
    @FXML
    private TableColumn<EntradaBCP, String> registroColumn;
    @FXML
    private TableColumn<EntradaBCP, Integer> acColumn;
    @FXML
    private TableColumn<EntradaBCP, Integer> dirInicioColumn;
    @FXML
    private TableColumn<EntradaBCP, Integer> dirFinColumn;
    @FXML
    private TableColumn<EntradaBCP, Integer> sigBCPColumn;

    //Tabla ejecucion
    @FXML
    private TableView<EntradaEjecucion> tablaEjecucion;
    @FXML
    private TableColumn<EntradaEjecucion, Integer> pidColumnEjecucion;

    private SistemaOperativo sistemaOperativo;


    @FXML
    public void initialize() {
        sistemaOperativo = new SistemaOperativo();

        this.fileManager = new FileManager();

        rbFCFS.setToggleGroup(groupAlgoritmo);
        rbSRT.setToggleGroup(groupAlgoritmo);
        rbSJF.setToggleGroup(groupAlgoritmo);
        rbRR.setToggleGroup(groupAlgoritmo);
        rbHRRN.setToggleGroup(groupAlgoritmo);

        rbPaginacion.setToggleGroup(groupAsignacion);
        rbParticionamientoDinamico.setToggleGroup(groupAsignacion);
        rbParticionamientoFijo.setToggleGroup(groupAsignacion);

        rbCPU1.setToggleGroup(groupCPU);
        rbCPU2.setToggleGroup(groupCPU);
        rbCPU3.setToggleGroup(groupCPU);
        rbCPU4.setToggleGroup(groupCPU);

        groupAlgoritmo.selectedToggleProperty().addListener((observable, oldVal, newVal) -> {
            RadioButton selectedRadioButton = (RadioButton) groupAlgoritmo.getSelectedToggle();
            switch (selectedRadioButton.getText()) {
                case "FCFS" -> Configuracion.getInstance().setExecutionStrategy(new FCFSExecution());
                case "SRT" -> Configuracion.getInstance().setExecutionStrategy(new SRTExecution());

                // ... casos para otros algoritmos
            }
        });

        // Similar para los otros grupos de Toggle (memoria, cantidad de CPU, etc.)
        groupAsignacion.selectedToggleProperty().addListener((observable, oldVal, newVal) -> {
            RadioButton selectedRadioButton = (RadioButton) groupAsignacion.getSelectedToggle();
            switch (selectedRadioButton.getText()) {
                case "Paginación (física y virtual)", "Particionamiento dinámico", "Particionamiento fijo" ->
                        Configuracion.getInstance().setMemoryAllocation(selectedRadioButton.getText());
            }
        });

        groupCPU.selectedToggleProperty().addListener((observable, oldVal, newVal) -> {
            RadioButton selectedButton = (RadioButton) groupCPU.getSelectedToggle();
            switch (selectedButton.getText()) {
                case "1", "2", "3", "4" ->
                        Configuracion.getInstance().setCpuCount(Integer.parseInt(selectedButton.getText()));
            }
        });

        politicaAsig.getItems().addAll("First-fit", "Best-fit", "Worse-fit");
        quantum.textProperty().addListener((observable, oldValue, newValue) -> {
            Configuracion.getInstance().setQuantum(Integer.parseInt(newValue));
        });
        //Paginacion
        pagesSize.textProperty().addListener((observable, oldValue, newValue) -> {
            Configuracion.getInstance().setPagesSize(Integer.parseInt(newValue));
            Configuracion.getInstance().actualizarParamsPaginacionPageSize(Integer.parseInt(newValue));
        });
        tablePagesSize.textProperty().addListener((observable, oldValue, newValue) -> {
            Configuracion.getInstance().setTablePagesSize(Integer.parseInt(newValue));
            Configuracion.getInstance().actualizarParamsPaginacionTablePagesSize(Integer.parseInt(newValue));
        });

        //Integer.parseInt(newValue)
        initialSize.textProperty().addListener((observable, oldValue, newValue) -> {
            Configuracion.getInstance().setInitialSize(Integer.parseInt(newValue));
        });
        politicaAsig.valueProperty().addListener((observable, oldValue, newValue) -> {
            Configuracion.getInstance().setPoliticaAsig(newValue);
        });

        //Particionamiento fijo
        numeroParticiones.textProperty().addListener((observable, oldValue, newValue) -> {
            Configuracion.getInstance().setNumeroParticiones(Integer.parseInt(newValue));
        });
        particionSize.textProperty().addListener((observable, oldValue, newValue) -> {
            Configuracion.getInstance().setParticionSize(Integer.parseInt(newValue));
        });

        //Tabla Disco
        columnaPos.setCellValueFactory(cellData -> cellData.getValue().getPosProperty().asObject());
        columnaValor.setCellValueFactory(cellData -> cellData.getValue().getValorProperty());
        tablaDisco.setItems(sistemaOperativo.getDisco().getObservables());

        //Tabla memoria
        columnaPosMemoria.setCellValueFactory(cellData -> cellData.getValue().getPosProperty().asObject());
        columnaValorMemoria.setCellValueFactory(cellData -> cellData.getValue().getValorProperty());
        tablaMemoria.setItems(sistemaOperativo.getObservablesMemoriaPrincipal());
        tablaMemoria.setRowFactory(row -> new TableRow<>() {
            @Override
            protected void updateItem(EntradaMemoria item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && item.getProcesoId() != null) {
                    String color = getColorForProcess(item.getProcesoId());
                    setStyle("-fx-background-color: " + color + ";");
                } else {
                    setStyle("");  // estilo por defecto si la fila está vacía o no tiene un proceso asociado
                }
            }
        });

        //Tabla CPU1
        pidColumn.setCellValueFactory(cellData -> cellData.getValue().pidProperty().asObject());
        estadoColumn.setCellValueFactory(cellData -> cellData.getValue().estadoProperty());
        tiempoCPUColum.setCellValueFactory(cellData -> cellData.getValue().tiempoCPUProperty().asObject());
        pcColumn.setCellValueFactory(cellData -> cellData.getValue().pcProperty().asObject());
        registroColumn.setCellValueFactory(cellData -> cellData.getValue().registroProperty());
        acColumn.setCellValueFactory(cellData -> cellData.getValue().acProperty().asObject());
        dirInicioColumn.setCellValueFactory(cellData -> cellData.getValue().dirInicioProperty().asObject());
        dirFinColumn.setCellValueFactory(cellData -> cellData.getValue().dirFinProperty().asObject());
        sigBCPColumn.setCellValueFactory(cellData -> cellData.getValue().sigBCPProperty().asObject());
        tablaCPU1.setItems(sistemaOperativo.getObservableListaBCP_CPU(0));

        //Todo: hacer el mismo observable para las otras 3 tablas de los 3 CPUs restantes

        //Agrega 25 columnas en la tabla ejecución
        agregarColumnas();
        pidColumnEjecucion.setCellValueFactory(cellData -> cellData.getValue().pidProperty().asObject());

    }

    @FXML
    private void onCargarArchivosClick() {
        // Suponiendo que el Stage principal esté disponible
        List<File> selectedFiles = fileManager.openMultipleFiles();

        if (selectedFiles != null && !selectedFiles.isEmpty()) {
            for (File file : selectedFiles) {
                try {
                    List<String> fileContent = fileManager.readFile(file);

                    if (fileManager.validateFileContent(fileContent)) {
                        // Haz algo con el contenido del archivo válido
                        System.out.println("Archivo valido!");
                        sistemaOperativo.cargarProcesosDisco(fileContent);
                        System.out.println("Proceso cargado a Disco!");
                    } else {
                        System.out.println("Archivo no valido!");
                    }
                } catch (IOException e) {
                    // Manejo de errores al leer el archivo
                }
            }
            sistemaOperativo.agregarToObservables();
        }
    }

    @FXML
    private void onEjecutarClick() {
        System.out.println(Configuracion.getInstance().getCpuCount());
        System.out.println("\n");
        System.out.println(Configuracion.getInstance().getMemoryAllocation());
        System.out.println("\n");
        System.out.println(Configuracion.getInstance().getCpuExecutionStrategy());
        System.out.println(Configuracion.getInstance().getQuantum());

//        System.out.println("Ejecutando paginacion-----------");
//        sistemaOperativo.ejecutar(this.tablaEjecucion);
//        System.out.println("Ejecutado paginacion------------");

        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                sistemaOperativo.ejecutar(tablaEjecucion);
                return null;
            }
        };
        new Thread(task).start();
    }

    @FXML
    private void onNextClick() {

    }

    private String getColorForProcess(String processId) {
        return switch (processId) {
            case "1" -> "red";
            case "2" -> "blue";
            case "3" -> "green";
            // ... otros casos ...
            default -> "gray";  // color por defecto para procesos no reconocidos o filas vacías
        };
    }

    public void agregarColumnas() {
        // Añadir 20 columnas al TableView
        for (int i = 1; i <= 20; i++) {
            TableColumn<EntradaEjecucion, String> column = new TableColumn<>(Integer.toString(i));
            column.setCellValueFactory(new PropertyValueFactory<>("Tiempo" + i));
            this.tablaEjecucion.getColumns().add(column);
        }

        tablaEjecucion.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }
}
