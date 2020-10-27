package com.example.one_handed_performance_test

import jxl.Workbook
import jxl.write.Label
import jxl.write.WritableSheet
import jxl.write.WritableWorkbook
import org.w3c.dom.Node
import java.io.File
import java.lang.Exception

class SaveToExcel(excelId: String) {
    private lateinit var wwb: WritableWorkbook
    private val excelFile: File = File(excelId)
    init {
        createExcel(excelFile)
    }
    private fun createExcel(file: File) {
        lateinit var writableSheet: WritableSheet
        try {
            if (!file.exists()) {
                wwb = Workbook.createWorkbook(file)
                writableSheet = wwb.createSheet("oneHandZoomExperimentData", 0)

                //创建标签label
                val r0c0 = Label(0, 0, "user")
                val r0c1 = Label(1, 0, "TO")
                val r0c2 = Label(2, 0, "ZC")
                val r0c3 = Label(3, 0, "CM")
                val r0c4 = Label(4, 0, "Tc")
                val r0c5 = Label(5, 0, "T")
                val r0c6 = Label(6, 0, "Ts")
                val r0c7 = Label(7, 0, "error")
                //根据标签在指定单元格插入数据
                writableSheet.addCell(r0c0)
                writableSheet.addCell(r0c1)
                writableSheet.addCell(r0c2)
                writableSheet.addCell(r0c3)
                writableSheet.addCell(r0c4)
                writableSheet.addCell(r0c5)
                writableSheet.addCell(r0c6)
                writableSheet.addCell(r0c7)
                //写入文件，释放内存
                wwb.write()
                wwb.close()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun writeToExcel(user: String, TO: String, ZC: String, CM: String,
                             Tc: Double, T: Double, Ts: Double, error: Double) {
        //读取现有工作簿
        val oldWwb = Workbook.getWorkbook(excelFile)
        //通过模板得到workbook, 第一个参数是输出流对象，第二个参数是读取的模板
        wwb = Workbook.createWorkbook(excelFile, oldWwb)
        //选中模板中的第一个工作表
        val ws = wwb.getSheet(0)
        //获取当前行数
        val recentRow = ws.rows
        //填充实验数据
        val labelOfUser = Label(0, recentRow, user)
        val labelOfTO = Label(1, recentRow, TO)
        val labelOfZC = Label(2, recentRow, ZC)
        val labelOfCM = Label(3, recentRow, CM)
        val numOfTc = jxl.write.Number(4, recentRow, Tc)
        val numOfT = jxl.write.Number(5, recentRow, T)
        val numOfTs = jxl.write.Number(6, recentRow, Ts)
        val numOferror = jxl.write.Number(7, recentRow, error)

        ws.addCell(labelOfUser)
        ws.addCell(labelOfTO)
        ws.addCell(labelOfZC)
        ws.addCell(labelOfCM)
        ws.addCell(numOfTc)
        ws.addCell(numOfT)
        ws.addCell(numOfTs)
        ws.addCell(numOferror)

        wwb.write()
        wwb.close()
    }

    private fun save(data: ExperimentData) {
        if (data == null)
            return;
        else try {
            writeToExcel(data.user, data.TO, data.ZC, data.CM, data.Tc, data.T, data.Ts, data.error)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}