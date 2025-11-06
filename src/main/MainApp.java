package main;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Prato;
import estrutura.TabelaHash;
import ordenacao.*;

public class MainApp extends Application {
    private TabelaHash tabela = new TabelaHash(10);
    private TableView<Prato> tabelaView = new TableView<>();
    private Label statusLabel = new Label("Sistema iniciado");
    private Label tempoLabel = new Label("");

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Cardápio Digital - Sistema de Gerenciamento");

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(15));
        root.setStyle("-fx-background-color: #f5f5f5;");

        VBox topPanel = createTopPanel();
        root.setTop(topPanel);

        VBox centerPanel = createCenterPanel();
        root.setCenter(centerPanel);

        HBox bottomPanel = createBottomPanel();
        root.setBottom(bottomPanel);

        VBox rightPanel = createRightPanel();
        root.setRight(rightPanel);

        Scene scene = new Scene(root, 1200, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox createTopPanel() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-background-color: #2c3e50; -fx-background-radius: 5;");

        Label titulo = new Label("CARDÁPIO DIGITAL");
        titulo.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label subtitulo = new Label("Sistema de Gerenciamento de Pratos");
        subtitulo.setStyle("-fx-font-size: 14px; -fx-text-fill: #ecf0f1;");

        vbox.getChildren().addAll(titulo, subtitulo);
        return vbox;
    }

    private VBox createCenterPanel() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(15));

        TableColumn<Prato, String> colNome = new TableColumn<>("Nome do Prato");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colNome.setPrefWidth(400);

        TableColumn<Prato, Double> colPreco = new TableColumn<>("Preço (R$)");
        colPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        colPreco.setPrefWidth(150);
        colPreco.setCellFactory(col -> new TableCell<Prato, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format("R$ %.2f", item));
                }
            }
        });

        TableColumn<Prato, Integer> colTempo = new TableColumn<>("Tempo (min)");
        colTempo.setCellValueFactory(new PropertyValueFactory<>("tempoPreparo"));
        colTempo.setPrefWidth(150);
        colTempo.setCellFactory(col -> new TableCell<Prato, Integer>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item + " min");
                }
            }
        });

        tabelaView.getColumns().addAll(colNome, colPreco, colTempo);
        tabelaView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tabelaView.setPlaceholder(new Label("Nenhum prato cadastrado"));

        vbox.getChildren().add(tabelaView);
        VBox.setVgrow(tabelaView, Priority.ALWAYS);

        return vbox;
    }

    private HBox createBottomPanel() {
        HBox hbox = new HBox(20);
        hbox.setPadding(new Insets(10));
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setStyle("-fx-background-color: #ecf0f1; -fx-background-radius: 5;");

        statusLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        tempoLabel.setStyle("-fx-text-fill: #7f8c8d;");

        hbox.getChildren().addAll(statusLabel, new Separator(), tempoLabel);
        return hbox;
    }

    private VBox createRightPanel() {
        VBox vbox = new VBox(15);
        vbox.setPadding(new Insets(15));
        vbox.setPrefWidth(350);
        vbox.setStyle("-fx-background-color: white; -fx-border-color: #bdc3c7; " +
                     "-fx-border-width: 0 0 0 2; -fx-border-radius: 5;");

        // Seção de Inserção
        Label lblInsercao = new Label("Inserir Novo Prato");
        lblInsercao.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #27ae60;");

        TextField txtNome = new TextField();
        txtNome.setPromptText("Nome do prato");

        TextField txtPreco = new TextField();
        txtPreco.setPromptText("Preço (ex: 25.90)");

        TextField txtTempo = new TextField();
        txtTempo.setPromptText("Tempo em minutos");

        Button btnInserir = new Button("Inserir Prato");
        btnInserir.setMaxWidth(Double.MAX_VALUE);
        btnInserir.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; " +
                           "-fx-font-weight: bold; -fx-padding: 10;");
        btnInserir.setOnAction(e -> {
            inserirPrato(txtNome.getText(), txtPreco.getText(), txtTempo.getText());
            txtNome.clear();
            txtPreco.clear();
            txtTempo.clear();
        });

        Separator sep1 = new Separator();

        // Seção de Busca
        Label lblBusca = new Label("Buscar Prato");
        lblBusca.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #3498db;");

        TextField txtBusca = new TextField();
        txtBusca.setPromptText("Nome do prato para buscar");

        Button btnBuscar = new Button("Buscar");
        btnBuscar.setMaxWidth(Double.MAX_VALUE);
        btnBuscar.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; " +
                          "-fx-font-weight: bold; -fx-padding: 10;");
        btnBuscar.setOnAction(e -> buscarPrato(txtBusca.getText()));

        Separator sep2 = new Separator();

        // Seção de Remoção
        Label lblRemocao = new Label("Remover Prato");
        lblRemocao.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #e74c3c;");

        TextField txtRemover = new TextField();
        txtRemover.setPromptText("Nome do prato para remover");

        Button btnRemover = new Button("Remover");
        btnRemover.setMaxWidth(Double.MAX_VALUE);
        btnRemover.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; " +
                           "-fx-font-weight: bold; -fx-padding: 10;");
        btnRemover.setOnAction(e -> {
            removerPrato(txtRemover.getText());
            txtRemover.clear();
        });

        Separator sep3 = new Separator();

        // Seção de Ordenação
        Label lblOrdenacao = new Label("Ordenar Pratos");
        lblOrdenacao.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #9b59b6;");

        ComboBox<String> comboCriterio = new ComboBox<>();
        comboCriterio.getItems().addAll("Nome", "Preço", "Tempo");
        comboCriterio.setPromptText("Selecione o critério");
        comboCriterio.setMaxWidth(Double.MAX_VALUE);

        ComboBox<String> comboAlgoritmo = new ComboBox<>();
        comboAlgoritmo.getItems().addAll("BubbleSort", "InsertionSort", "QuickSort");
        comboAlgoritmo.setPromptText("Selecione o algoritmo");
        comboAlgoritmo.setMaxWidth(Double.MAX_VALUE);

        Button btnOrdenar = new Button("Ordenar");
        btnOrdenar.setMaxWidth(Double.MAX_VALUE);
        btnOrdenar.setStyle("-fx-background-color: #9b59b6; -fx-text-fill: white; " +
                           "-fx-font-weight: bold; -fx-padding: 10;");
        btnOrdenar.setOnAction(e -> ordenarPratos(comboCriterio.getValue(), comboAlgoritmo.getValue()));

        Button btnLimpar = new Button("Limpar Filtros");
        btnLimpar.setMaxWidth(Double.MAX_VALUE);
        btnLimpar.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white; -fx-padding: 8;");
        btnLimpar.setOnAction(e -> atualizarTabela());

        vbox.getChildren().addAll(
            lblInsercao, txtNome, txtPreco, txtTempo, btnInserir,
            sep1,
            lblBusca, txtBusca, btnBuscar,
            sep2,
            lblRemocao, txtRemover, btnRemover,
            sep3,
            lblOrdenacao, comboCriterio, comboAlgoritmo, btnOrdenar, btnLimpar
        );

        return vbox;
    }

    private void inserirPrato(String nome, String precoStr, String tempoStr) {
        try {
            if (nome == null || nome.trim().isEmpty()) {
                showError("Nome do prato não pode estar vazio!");
                return;
            }

            double preco = Double.parseDouble(precoStr);
            int tempo = Integer.parseInt(tempoStr);

            if (preco <= 0 || tempo <= 0) {
                showError("Preço e tempo devem ser positivos!");
                return;
            }

            Prato prato = new Prato(nome.trim(), preco, tempo);
            tabela.inserir(prato);
            
            atualizarTabela();
            statusLabel.setText("Prato '" + nome + "' inserido com sucesso!");
            statusLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #27ae60;");
            
        } catch (NumberFormatException e) {
            showError("Preço e tempo devem ser números válidos!");
        } catch (IllegalArgumentException e) {
            showError(e.getMessage());
        }
    }

    private void buscarPrato(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            showError("Digite o nome do prato para buscar!");
            return;
        }

        Prato prato = tabela.buscar(nome.trim());
        
        if (prato != null) {
            tabelaView.getItems().clear();
            tabelaView.getItems().add(prato);
            tabelaView.getSelectionModel().select(prato);
            statusLabel.setText("Prato encontrado: " + prato.getNome());
            statusLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #3498db;");
        } else {
            showError("Prato '" + nome + "' não encontrado!");
        }
    }

    private void removerPrato(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            showError("Digite o nome do prato para remover!");
            return;
        }

        boolean removido = tabela.remover(nome.trim());
        
        if (removido) {
            atualizarTabela();
            statusLabel.setText("Prato '" + nome + "' removido com sucesso!");
            statusLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #e74c3c;");
        } else {
            showError("Prato '" + nome + "' não encontrado!");
        }
    }

    private void ordenarPratos(String criterio, String algoritmo) {
        if (criterio == null || algoritmo == null) {
            showError("Selecione o critério e o algoritmo de ordenação!");
            return;
        }

        if (tabela.isEmpty()) {
            showError("Não há pratos para ordenar!");
            return;
        }

        Prato[] vetor = tabela.exportarParaVetor();
        String criterioLower = criterio.toLowerCase();

        long inicio = System.nanoTime();

        switch (algoritmo) {
            case "BubbleSort" -> BubbleSort.ordenar(vetor, criterioLower);
            case "InsertionSort" -> InsertionSort.ordenar(vetor, criterioLower);
            case "QuickSort" -> QuickSort.ordenar(vetor, criterioLower);
        }

        long fim = System.nanoTime();
        double tempoMs = (fim - inicio) / 1_000_000.0;

        tabelaView.getItems().clear();
        tabelaView.getItems().addAll(vetor);

        statusLabel.setText(String.format("📊 Ordenado por %s usando %s", criterio, algoritmo));
        statusLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #9b59b6;");
        
        tempoLabel.setText(String.format("⏱️ Tempo: %.4f ms | Total: %d pratos", tempoMs, vetor.length));
    }

    private void atualizarTabela() {
        Prato[] pratos = tabela.exportarParaVetor();
        tabelaView.getItems().clear();
        tabelaView.getItems().addAll(pratos);
        
        statusLabel.setText("Exibindo todos os pratos (" + pratos.length + " itens)");
        statusLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        tempoLabel.setText("");
    }

    private void showError(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
        
        statusLabel.setText(mensagem);
        statusLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #e74c3c;");
    }

    public static void main(String[] args) {
        launch(args);
    }
}