package com.aps.aps;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import system.Event;
import system.SystemManager;

import java.util.*;

public class applicationController {
    @FXML
    private Label sourceLabel;
    @FXML
    private Label deviceLabel;
    @FXML
    private GridPane root;
    @FXML
    private Label errorLabel;
    @FXML
    private Label requestLabel;
    @FXML
    private Label bufferLabel;
    @FXML
    private TextField sourceCount;
    @FXML
    private TextField deviceCount;
    @FXML
    private TextField requestCount;
    @FXML
    private Button stepMode;
    @FXML Button autoMode;
    @FXML
    private TextField bufferSize;
    @FXML
    private VBox waveForm;
    @FXML
    private ScrollPane scroll;
    private LinkedList<LineChart<Number,Number>> lineCharts;
    private int parameters[];
    private ObservableList<EventInfo> eventInfo = FXCollections.observableArrayList();
    private LinkedList<XYChart.Series> series = new LinkedList<>();
    private TableView<EventInfo> table;
    private TableView<SourceInfo> sourceTable;
    private TableView<DeviceInfo> deviceTable;
    @FXML
    private Button nextStep;
    private int eventNum = 0;
    private List<Event> events;

    @FXML
    private void initialize() {
        sourceCount.setMaxWidth(40);
        deviceCount.setMaxWidth(40);
        requestCount.setMaxWidth(40);
        bufferSize.setMaxWidth(40);
    }
    @FXML
    protected void drawNextStep()
    {
        Event temp = events.get(eventNum++);
        if(temp.getSourceNum() != -1)
        {
            for(int i = 0; i < parameters[0]; i++)
            {
                if(temp.getSourceNum() == i)
                {
                    series.get(temp.getSourceNum()).getData().add(new XYChart.Data(temp.getEventTime(), 0));
                    series.get(temp.getSourceNum()).getData().add(new XYChart.Data(temp.getEventTime()+0.0001, 1));
                    series.get(temp.getSourceNum()).getData().add(new XYChart.Data(temp.getEventTime()+0.0002, 0));
                    lineCharts.get(temp.getSourceNum()).getData().clear();
                    lineCharts.get(temp.getSourceNum()).getData().add(series.get(temp.getSourceNum()));
                }
                else
                {
                    series.get(i).getData().add(new XYChart.Data(temp.getEventTime(), 0));
                    lineCharts.get(i).getData().clear();
                    lineCharts.get(i).getData().add(series.get(i));
                }
            }
        }
        else
        {
            for(int i = 0; i < parameters[0]; i++)
            {
                series.get(i).getData().add(new XYChart.Data(temp.getEventTime(), 0));
                lineCharts.get(i).getData().clear();
                lineCharts.get(i).getData().add(series.get(i));
            }
        }
        if(temp.getDeviceNum() != -1)
        {
            for(int i = parameters[0]; i < parameters[0] + parameters[2]; i++)
            {
                if(temp.getDeviceNum() + parameters[0] == i)
                {
                    Number busy = lineCharts.get(i).getData().get(lineCharts.get(i).getData().size() - 1).getData().get(lineCharts.get(i).getData().get(lineCharts.get(i).getData().size() - 1).getData().size() - 1).getYValue();
                    if(Double.compare(busy.doubleValue(), 0) == 0)
                    {
                        series.get(i).getData().add(new XYChart.Data(temp.getEventTime(), 0));
                        series.get(i).getData().add(new XYChart.Data(temp.getEventTime()+0.0001, 1));
                        lineCharts.get(i).getData().clear();
                        lineCharts.get(i).getData().add(series.get(i));
                    }
                    else
                    {
                        series.get(i).getData().add(new XYChart.Data(temp.getEventTime(), 1));
                        series.get(i).getData().add(new XYChart.Data(temp.getEventTime()+0.0001, 0));
                        lineCharts.get(i).getData().clear();
                        lineCharts.get(i).getData().add(series.get(i));
                    }
                }
                else
                {
                    Number busy = lineCharts.get(i).getData().get(lineCharts.get(i).getData().size() - 1).getData().get(lineCharts.get(i).getData().get(lineCharts.get(i).getData().size() - 1).getData().size() - 1).getYValue();
                    if(Double.compare(busy.doubleValue(), 0) == 0)
                    {
                        series.get(i).getData().add(new XYChart.Data(temp.getEventTime(), 0));
                        lineCharts.get(i).getData().clear();
                        lineCharts.get(i).getData().add(series.get(i));
                    }
                    else
                    {
                        series.get(i).getData().add(new XYChart.Data(temp.getEventTime(), 1));
                        lineCharts.get(i).getData().clear();
                        lineCharts.get(i).getData().add(series.get(i));
                    }
                }
            }
        }
        else
        {
            for(int i = parameters[0]; i < parameters[0] + parameters[2]; i++)
            {
                Number busy = lineCharts.get(i).getData().get(lineCharts.get(i).getData().size() - 1).getData().get(lineCharts.get(i).getData().get(lineCharts.get(i).getData().size() - 1).getData().size() - 1).getYValue();
                if(Double.compare(busy.doubleValue(), 0) == 0)
                {
                    series.get(i).getData().add(new XYChart.Data(temp.getEventTime(), 0));
                    lineCharts.get(i).getData().clear();
                    lineCharts.get(i).getData().add(series.get(i));
                }
                else
                {
                    series.get(i).getData().add(new XYChart.Data(temp.getEventTime(), 1));
                    lineCharts.get(i).getData().clear();
                    lineCharts.get(i).getData().add(series.get(i));
                }
            }
        }
        if (temp.getBufferStatus() != -1)
        {
            if(temp.getBufferStatus() == 0)
            {
                series.get(series.size() - 2).getData().add(new XYChart.Data(temp.getEventTime(), 0));
                series.get(series.size() - 2).getData().add(new XYChart.Data(temp.getEventTime()+0.0001, 1));
                lineCharts.get(lineCharts.size() - 2).getData().clear();
                lineCharts.get(lineCharts.size() - 2).getData().add(series.get(series.size() - 2));
            }
            if(temp.getBufferStatus() == 3)
            {
                series.get(series.size() - 2).getData().add(new XYChart.Data(temp.getEventTime(), 1));
                series.get(series.size() - 2).getData().add(new XYChart.Data(temp.getEventTime()+0.0001, 0));
                lineCharts.get(lineCharts.size() - 2).getData().clear();
                lineCharts.get(lineCharts.size() - 2).getData().add(series.get(series.size() - 2));
            }
            if(temp.getBufferStatus() == 2)
            {
                series.get(series.size() - 1).getData().add(new XYChart.Data(temp.getEventTime(), 0));
                series.get(series.size() - 1).getData().add(new XYChart.Data(temp.getEventTime()+0.0001, 1));
                series.get(series.size() - 1).getData().add(new XYChart.Data(temp.getEventTime()+0.0002, 0));
                lineCharts.get(lineCharts.size() - 1).getData().clear();
                lineCharts.get(lineCharts.size() - 1).getData().add(series.get(series.size() - 1));
            }
        }
        else
        {
            Number busy = lineCharts.get(series.size() - 2).getData().get(lineCharts.get(series.size() - 2).getData().size() - 1).getData().get(lineCharts.get(series.size() - 2).getData().get(lineCharts.get(series.size() - 2).getData().size() - 1).getData().size() - 1).getYValue();
            if(Double.compare(busy.doubleValue(), 0) == 0)
            {
                series.get(series.size() - 2).getData().add(new XYChart.Data(temp.getEventTime(), 0));
                lineCharts.get(series.size() - 2).getData().clear();
                lineCharts.get(series.size() - 2).getData().add(series.get(series.size() - 2));
            }
            else
            {
                series.get(series.size() - 2).getData().add(new XYChart.Data(temp.getEventTime(), 1));
                lineCharts.get(series.size() - 2).getData().clear();
                lineCharts.get(series.size() - 2).getData().add(series.get(series.size() - 2));
            }
        }
        series.get(series.size() - 1).getData().add(new XYChart.Data(temp.getEventTime(), 0));
        lineCharts.get(lineCharts.size() - 1).getData().clear();
        lineCharts.get(lineCharts.size() - 1).getData().add(series.get(series.size() - 1));
        eventInfo.add(new EventInfo(temp.getStatus(), temp.getPlacement(), temp.getEventTime()));
        table.setItems(eventInfo);
        if(eventNum >= events.size())
        {
            for(int i = 0; i < parameters[0]; i++)
            {
                series.get(i).getData().add(new XYChart.Data(temp.getEventTime(), 0));
                lineCharts.get(i).getData().clear();
                lineCharts.get(i).getData().add(series.get(i));
            }
            for(int i = parameters[0]; i < parameters[0] + parameters[2]; i++)
            {
                Number busy = lineCharts.get(i).getData().get(lineCharts.get(i).getData().size() - 1).getData().get(lineCharts.get(i).getData().get(lineCharts.get(i).getData().size() - 1).getData().size() - 1).getYValue();
                if(Double.compare(busy.doubleValue(), 0) == 0)
                {
                    series.get(i).getData().add(new XYChart.Data(temp.getEventTime(), 0));
                    lineCharts.get(i).getData().clear();
                    lineCharts.get(i).getData().add(series.get(i));
                }
                else
                {
                    series.get(i).getData().add(new XYChart.Data(temp.getEventTime(), 1));
                    series.get(i).getData().add(new XYChart.Data(temp.getEventTime() + 0.0001, 0));
                    lineCharts.get(i).getData().clear();
                    lineCharts.get(i).getData().add(series.get(i));
                }
            }
            Number busy = lineCharts.get(series.size() - 2).getData().get(lineCharts.get(series.size() - 2).getData().size() - 1).getData().get(lineCharts.get(series.size() - 2).getData().get(lineCharts.get(series.size() - 2).getData().size() - 1).getData().size() - 1).getYValue();
            if(Double.compare(busy.doubleValue(), 0) == 0)
            {
                series.get(series.size() - 2).getData().add(new XYChart.Data(temp.getEventTime(), 0));
                lineCharts.get(series.size() - 2).getData().clear();
                lineCharts.get(series.size() - 2).getData().add(series.get(series.size() - 2));
            }
            else
            {
                series.get(series.size() - 2).getData().add(new XYChart.Data(temp.getEventTime(), 1));
                series.get(series.size() - 2).getData().add(new XYChart.Data(temp.getEventTime() + 0.0001, 1));
                lineCharts.get(series.size() - 2).getData().clear();
                lineCharts.get(series.size() - 2).getData().add(series.get(series.size() - 2));
            }
            series.get(series.size() - 1).getData().add(new XYChart.Data(temp.getEventTime(), 0));
            lineCharts.get(lineCharts.size() - 1).getData().clear();
            lineCharts.get(lineCharts.size() - 1).getData().add(series.get(series.size() - 1));
            /*for(int i = 0; i < eventInfo.size(); i++)
            {
                System.out.println(eventInfo.get(i).getStatus() + " " + eventInfo.get(i).getPlacement());
            }*/
            table.setItems(eventInfo);
            nextStep.setVisible(false);
        }
    }
    @FXML
    protected void openAutoMode() {
        TextField temp[] = new TextField[4];
        temp[0] = sourceCount;
        temp[1] = requestCount;
        temp[2] = deviceCount;
        temp[3] = bufferSize;
        parameters = new int[4];
        for(int i = 0; i < 4; i++)
        {
            try
            {
                parameters[i] = Integer.parseInt(temp[i].getCharacters().toString());
            } catch (NumberFormatException ex)
            {
                errorLabel.setVisible(true);
                return;
            }
        }
        system.SystemManager sys = new SystemManager(parameters[0], parameters[1], parameters[2], parameters[3]);
        events = sys.simulateSystem();
        errorLabel.setVisible(false);
        stepMode.setVisible(false);
        autoMode.setVisible(false);
        sourceLabel.setVisible(false);
        deviceLabel.setVisible(false);
        requestLabel.setVisible(false);
        bufferLabel.setVisible(false);
        sourceCount.setVisible(false);
        deviceCount.setVisible(false);
        requestCount.setVisible(false);
        bufferSize.setVisible(false);
        sourceTable = new TableView();
        TableColumn sourceNumCol = new TableColumn("Номер источника");
        sourceNumCol.setCellValueFactory(new PropertyValueFactory<>("sourceNum"));
        TableColumn rejPerCol = new TableColumn("Процент отказов");
        rejPerCol.setCellValueFactory(new PropertyValueFactory<>("rejectionPercentage"));
        TableColumn timeInBufCol = new TableColumn("Время нахождения заявок в буфере");
        timeInBufCol.setCellValueFactory(new PropertyValueFactory<>("bufferRequestTime"));
        TableColumn timeInDevCol = new TableColumn("Время нахождения заявок в устройстве");
        timeInDevCol.setCellValueFactory(new PropertyValueFactory<>("deviceRequestTime"));
        TableColumn timeInSysCol = new TableColumn("Время нахождения заявок в системе");
        timeInSysCol.setCellValueFactory(new PropertyValueFactory<>("systemRequestTime"));
        sourceTable.getColumns().add(sourceNumCol);
        sourceTable.getColumns().add(rejPerCol);
        sourceTable.getColumns().add(timeInBufCol);
        sourceTable.getColumns().add(timeInDevCol);
        sourceTable.getColumns().add(timeInSysCol);
        sourceTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        sourceTable.setMinWidth(1200);
        deviceTable = new TableView<>();
        TableColumn deviceNumCol = new TableColumn("Номер устройства");
        deviceNumCol.setCellValueFactory(new PropertyValueFactory<>("deviceNum"));
        TableColumn workLoadCol = new TableColumn("Процент загрузки");
        workLoadCol.setCellValueFactory(new PropertyValueFactory<>("workloadPercentage"));
        deviceTable.getColumns().add(deviceNumCol);
        deviceTable.getColumns().add(workLoadCol);
        deviceTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        deviceTable.setMinWidth(400);
        root.add(sourceTable, 0,0);
        root.add(deviceTable, 1, 0);
        ObservableList<SourceInfo> sourceInfo = FXCollections.observableArrayList();
        for(int i = 0; i < parameters[0]; i++)
        {
            SourceInfo srcInf = new SourceInfo(Integer.toString(i), SystemInfo.rejectionPercentage.get(i), SystemInfo.bufferRequestTime.get(i), SystemInfo.deviceRequestTime.get(i), SystemInfo.systemRequestTime.get(i));
            sourceInfo.add(srcInf);
        }
        SourceInfo srcInf = new SourceInfo("Общ. стат.",
                SystemInfo.rejectionPercentage.stream().reduce(0.0, Double::sum) / parameters[0],
                SystemInfo.bufferRequestTime.stream().reduce(0.0, Double::sum) / parameters[0],
                SystemInfo.deviceRequestTime.stream().reduce(0.0, Double::sum) / parameters[0],
                SystemInfo.systemRequestTime.stream().reduce(0.0, Double::sum) / parameters[0]);
        sourceInfo.add(srcInf);
        sourceTable.setItems(sourceInfo);
        ObservableList<DeviceInfo> deviceInfo = FXCollections.observableArrayList();
        for(int i = 0; i < parameters[2]; i++)
        {
            //System.out.println("device " + i);
            //System.out.println(SystemInfo.workloadPercentage.get(i));
            DeviceInfo devInf = new DeviceInfo(Integer.toString(i), SystemInfo.workloadPercentage.get(i));
            deviceInfo.add(devInf);
        }
        DeviceInfo deviceInf = new DeviceInfo("Общ. стат.", SystemInfo.workloadPercentage.stream().reduce(0.0, Double::sum) / parameters[2]);
        deviceInfo.add(deviceInf);
        deviceTable.setItems(deviceInfo);
    }

    @FXML
    protected void openStepMode() {
        TextField temp[] = new TextField[4];
        temp[0] = sourceCount;
        temp[1] = requestCount;
        temp[2] = deviceCount;
        temp[3] = bufferSize;
        parameters = new int[4];
        for(int i = 0; i < 4; i++)
        {
            try
            {
                parameters[i] = Integer.parseInt(temp[i].getCharacters().toString());
            } catch (NumberFormatException ex)
            {
                errorLabel.setVisible(true);
                return;
            }
        }
        system.SystemManager sys = new SystemManager(parameters[0], parameters[1], parameters[2], parameters[3]);
        events = sys.simulateSystem();
        stepMode.setVisible(false);
        autoMode.setVisible(false);
        sourceLabel.setVisible(false);
        deviceLabel.setVisible(false);
        requestLabel.setVisible(false);
        bufferLabel.setVisible(false);
        sourceCount.setVisible(false);
        deviceCount.setVisible(false);
        requestCount.setVisible(false);
        bufferSize.setVisible(false);
        lineCharts = new LinkedList<>();
        for(int i = 0; i < parameters[0] + parameters[2] + 1; i++)
        {
            series.add(new XYChart.Series());
            series.get(i).getData().add(new XYChart.Data(0,0));
            NumberAxis xAxis = new NumberAxis();
            xAxis.setTickUnit(1);
            NumberAxis yAxis = new NumberAxis();
            xAxis.setAutoRanging(false);
            xAxis.setTickLength(0.5);
            xAxis.setUpperBound(events.get(events.size() - 1).getEventTime());
            yAxis.setUpperBound(1);
            xAxis.setLabel("Время(мс)");
            lineCharts.add(new LineChart<Number,Number>(xAxis,yAxis));
            lineCharts.get(i).getData().add(series.get(i));
            lineCharts.getLast().setMaxHeight(5);
            lineCharts.getLast().setMaxWidth(900);
            lineCharts.getLast().setLegendVisible(false);
            if((i / parameters[0]) == 0)
            {
                lineCharts.getLast().setTitle("Источник " + i);
            } else if (((i-parameters[0]) / parameters[2] == 0))
            {
                lineCharts.getLast().setTitle("Прибор " + (i - parameters[0]));
            }
            else
            {
                lineCharts.getLast().setTitle("Буфер (размер - " + parameters[3] + ")");
            }
            lineCharts.getLast().setCreateSymbols(false);
            lineCharts.getLast().setAnimated(false);
            lineCharts.getLast().getYAxis().setTickLabelsVisible(false);
            waveForm.getChildren().add(lineCharts.getLast());
        }
        series.add(new XYChart.Series());
        series.get(series.size() - 1).getData().add(new XYChart.Data(0,0));
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setAutoRanging(false);
        xAxis.setTickLength(0.5);
        xAxis.setUpperBound(events.get(events.size() - 1).getEventTime());
        yAxis.setUpperBound(1);
        xAxis.setLabel("Время(мс)");
        lineCharts.add(new LineChart<Number,Number>(xAxis,yAxis));
        lineCharts.getLast().setTitle("Отказ");
        lineCharts.get(lineCharts.size() - 1).getData().add(series.get(series.size() - 1));
        lineCharts.getLast().setMaxHeight(5);
        lineCharts.getLast().setMaxWidth(900);
        lineCharts.getLast().setLegendVisible(false);
        lineCharts.getLast().setCreateSymbols(false);
        lineCharts.getLast().setAnimated(false);
        lineCharts.getLast().getYAxis().setTickLabelsVisible(false);
        waveForm.getChildren().add(lineCharts.getLast());
        scroll.setVisible(true);
        scroll.setMinWidth(550);
        waveForm.setVisible(true);
        table = new TableView();
        TableColumn statusCol = new TableColumn("Статус");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusCol.setMinWidth(events.stream().max(Comparator.comparing(Event::getStatus)).get().getStatus().length()*10);
        TableColumn discCol = new TableColumn("Описание");
        discCol.setMinWidth(events.stream().max(Comparator.comparing(Event::getPlacement)).get().getPlacement().length()*8);
        discCol.setCellValueFactory(new PropertyValueFactory<>("placement"));
        TableColumn timeCol = new TableColumn("Время");
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        table.getColumns().add(statusCol);
        table.getColumns().add(discCol);
        table.getColumns().add(timeCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setMinWidth(800);
        root.add(table, 1,0);
        nextStep.setVisible(true);
    }
}