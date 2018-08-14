package com.thoughtworks.bahmnidhis2integrationservice.controller;

import com.thoughtworks.bahmnidhis2integrationservice.exception.NoMappingFoundException;
import com.thoughtworks.bahmnidhis2integrationservice.service.impl.MappingServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.thoughtworks.bahmnidhis2integrationservice.CommonTestHelper.setValuesForMemberFields;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
public class MappingControllerTest {

    private MappingController mappingController;

    @Mock
    private MappingServiceImpl mappingService;

    private String mappingName = "patient_details";
    private String lookupTable = "patient";
    private String mappingJson = "{'patient_id': 'Asj8X', 'patient_name': 'jghTk9'}";
    private String currentMapping = "pat_details";


    private Map<String, String> params = new HashMap<>();

    @Before
    public void setUp() throws Exception {
        mappingController = new MappingController();
        setValuesForMemberFields(mappingController, "mappingService", mappingService);
        params.put("mappingName", mappingName);
        params.put("lookupTable", lookupTable);
        params.put("mappingJson", mappingJson);
        params.put("currentMapping", currentMapping);
    }

    @Test
    public void shouldSaveMappings() throws Exception {
        String expected = "Successfully Added Mapping";

        when(mappingService.saveMapping(mappingName, lookupTable, mappingJson, currentMapping)).thenReturn(expected);

        Map<String, String> actual = mappingController.saveMappings(params);

        verify(mappingService, times(1))
                .saveMapping(mappingName, lookupTable, mappingJson, currentMapping);
        assertEquals(expected, actual.get("data"));
    }

    @Test
    public void shouldThrowErrorOnFail() throws Exception {
        String expected = "Could not able to add Mapping";

        when(mappingService.saveMapping(mappingName, lookupTable, mappingJson, currentMapping))
                .thenThrow(new Exception(expected));

        try {
            mappingController.saveMappings(params);
        } catch (Exception e) {
            verify(mappingService, times(1))
                    .saveMapping(mappingName, lookupTable, mappingJson, currentMapping);
            assertEquals(expected, e.getMessage());
        }
    }

    @Test
    public void shouldReturnAllMappingNames() {
        List<String> expected = Arrays.asList("HTS Service","TB Service");
        when(mappingService.getMappingNames()).thenReturn(expected);

        List<String> allMappings = mappingController.getAllMappingNames();

        assertEquals(allMappings,expected);
    }

    @Test
    public void shouldReturnExistingMappingName() throws NoMappingFoundException {
        Map<String, Object> HTSMapping = new HashMap<>();

        HTSMapping.put("mapping_name","HTS Service");
        HTSMapping.put("lookup_table","{\"instance\" : \"patient\"}");
        HTSMapping.put("mapping_json","{\"instance\" : {\"patient_id\": \"Asj8X\", \"patient_name\": \"jghTk9\"}}");

        when(mappingService.getMapping("HTS Service")).thenReturn(HTSMapping);

        assertEquals(HTSMapping, mappingController.getMapping("HTS Service"));

        verify(mappingService, times(1)).getMapping("HTS Service");
    }

    @Test
    public void shouldThrowEmptyResultDataAccessExceptionWhenThereIsNoMapping() throws NoMappingFoundException {
        String mappingName = "someMapping";

        when(
                mappingService.getMapping(mappingName)
        ).thenThrow(new NoMappingFoundException(mappingName));

        try{
            mappingController.getMapping(mappingName);
        }catch (NoMappingFoundException e){
            assertEquals(e.getMessage(),"No mapping found with name "+mappingName);
        }
    }
}