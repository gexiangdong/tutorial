package cn.devmgr.tutorial.controller;

import org.apache.ibatis.javassist.tools.rmi.Sample;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sample")
public class SampleController {
    private static final Logger logger = LoggerFactory.getLogger(SampleController.class);


    /**
     * 导出Excel的例子
     * 因为类上面注解是@Controller，此方法需要@ResponseBody注解；如果类是RestController，则不需要ResponseBody
     * @return
     * @throws Exception
     */
    @ResponseBody
    @GetMapping("/export")
    public ResponseEntity<byte[]> exportExcel() throws Exception{
        logger.trace("exportExcel");
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentDispositionFormData("attachment",new String("导出的文件名.xlsx".getBytes(), "ISO8859-1"));
        responseHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        //中文文件名需要用iso8859-1编码
        InputStream templateIs = this.getClass().getResourceAsStream("/excel-templates/templet.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(templateIs);
        XSSFSheet sheet = workbook.getSheetAt(0);

        List<SampleItem> list = getDataList();

        CellStyle cellStyle = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy/mm/dd"));

        for (int i=0; i<list.size(); i++) {
            SampleItem si = list.get(i);

            XSSFRow row = sheet.createRow(i + 1);

            Cell cell1 = row.createCell(0);
            cell1.setCellValue(si.getDate());
            cell1.setCellStyle(cellStyle);

            Cell cell2 = row.createCell(1);
            cell2.setCellValue(si.getName());

            Cell cell3 = row.createCell(2);
            cell3.setCellValue(si.getScore());
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        workbook.write(bos);
        workbook.close();
        return new ResponseEntity<byte[]>(bos.toByteArray(), responseHeaders, HttpStatus.OK);
    }

    private List<SampleItem> getDataList(){
        List<SampleItem> data = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        SampleItem si1 = new SampleItem("张三", c.getTime(), 98.5f);
        c.add(Calendar.DAY_OF_YEAR, -872);
        SampleItem si2 = new SampleItem("李四", c.getTime(), 82f);
        data.add(si1);
        data.add(si2);
        return data;
    }


    /**
     * 例子POJO
     */
    public class SampleItem{
        private float score;
        private Date date;
        private String name;

        public SampleItem(String name, Date date, float score){
            this.name = name;
            this.date = date;
            this.score = score;
        }

        public float getScore() {
            return score;
        }

        public void setScore(float score) {
            this.score = score;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }


}


