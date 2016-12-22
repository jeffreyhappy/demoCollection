package com.wswy.scrollpanel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ScrollablePanel scrollablePanel = (ScrollablePanel) findViewById(R.id.scrollable_panel);
        ScrollablePanelAdapter scrollablePanelAdapter = new ScrollablePanelAdapter();
        generateTestData(scrollablePanelAdapter);
        scrollablePanel.setPanelAdapter(scrollablePanelAdapter);

    }

    private void generateTestData(ScrollablePanelAdapter scrollablePanelAdapter) {
        ArrayList<String> rowList = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            String  rowString = "row " + i;
            rowList.add(rowString);
        }
        scrollablePanelAdapter.setRowList(rowList);


        ArrayList<String> columnList = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            String  rowString = "row " + i;
            columnList.add(rowString);
        }
        scrollablePanelAdapter.setColumnList(columnList);
//        List<String> rowList = new ArrayList<>();
//        for (int i = 0; i < 6; i++) {
//            String  rowString = "row " + i;
//            rowList.add(rowString);
//        }
//        List<RoomInfo> roomInfoList = new ArrayList<>();
//        for (int i = 0; i < 6; i++) {
//            RoomInfo roomInfo = new RoomInfo();
//            roomInfo.setRoomType("SUPK");
//            roomInfo.setRoomId(i);
//            roomInfo.setRoomName("20" + i);
//            roomInfoList.add(roomInfo);
//        }
//        for (int i = 6; i < 30; i++) {
//            RoomInfo roomInfo = new RoomInfo();
//            roomInfo.setRoomType("Standard");
//            roomInfo.setRoomId(i);
//            roomInfo.setRoomName("30" + i);
//            roomInfoList.add(roomInfo);
//        }
//        scrollablePanelAdapter.setRoomInfoList(roomInfoList);
//
//        List<DateInfo> dateInfoList = new ArrayList<>();
//        Calendar calendar = Calendar.getInstance();
//        for (int i = 0; i < 14; i++) {
//            DateInfo dateInfo = new DateInfo();
//            String date = DAY_UI_MONTH_DAY_FORMAT.format(calendar.getTime());
//            String week = WEEK_FORMAT.format(calendar.getTime());
//            dateInfo.setDate(date);
//            dateInfo.setWeek(week);
//            dateInfoList.add(dateInfo);
//            calendar.add(Calendar.DAY_OF_MONTH, 1);
//        }
//
//        scrollablePanelAdapter.setDateInfoList(dateInfoList);
//
//        List<List<OrderInfo>> ordersList = new ArrayList<>();
//        for (int i = 0; i < 30; i++) {
//            List<OrderInfo> orderInfoList = new ArrayList<>();
//            for (int j = 0; j < 14; j++) {
//                OrderInfo orderInfo = new OrderInfo();
//                orderInfo.setGuestName("NO." + i + j);
//                orderInfo.setBegin(true);
//                orderInfo.setStatus(OrderInfo.Status.randomStatus());
//                if (orderInfoList.size() > 0) {
//                    OrderInfo lastOrderInfo = orderInfoList.get(orderInfoList.size() - 1);
//                    if (orderInfo.getStatus().ordinal() == lastOrderInfo.getStatus().ordinal()) {
//                        orderInfo.setId(lastOrderInfo.getId());
//                        orderInfo.setBegin(false);
//                        orderInfo.setGuestName("");
//                    } else {
//                        if (new Random().nextBoolean()) {
//                            orderInfo.setStatus(OrderInfo.Status.BLANK);
//                        }
//                    }
//                }
//                orderInfoList.add(orderInfo);
//            }
//            ordersList.add(orderInfoList);
//        }
//        scrollablePanelAdapter.setOrdersList(ordersList);
    }
}
