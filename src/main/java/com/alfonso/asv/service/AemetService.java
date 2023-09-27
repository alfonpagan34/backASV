package com.alfonso.asv.service;

import com.alfonso.asv.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class AemetService {

    private static final int SUCCESS_CODE = 200;
    private static final Map<String,Float> UNIDADES_TEMPERATURA = new HashMap<>();
    static {
        UNIDADES_TEMPERATURA.put("G_CEL", (float) 0);
        UNIDADES_TEMPERATURA.put("G_FAH", (float) 32);
        UNIDADES_TEMPERATURA.put("G_KEL", (float) 273);
    }

    private RestTemplate restTemplate;

    private HttpHeaders headers;

    @Value("${aemet.baseurl}")
    private String aemetBaseUrl;

    public AemetService(@Value("${aemet.token}") String token) {
        this.restTemplate = new RestTemplate();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Arrays.asList(MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON));
        restTemplate.getMessageConverters().add(0, converter);

        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("api_key", token);
    }

    public List<Municipio> getMunicipios() {
        HttpEntity<String> request = new HttpEntity<>(headers);
        String url = generateUrl("/api/maestro/municipios");

        ResponseEntity<Municipio[]> result = restTemplate.exchange(url, HttpMethod.GET, request, Municipio[].class);
        if (result.getBody() != null)
            return Arrays.asList(result.getBody());
        return new ArrayList<>();
    }

    public PrediccionResponse getPrediccion(String codigoMunicipio,String unidad) {
        HttpEntity<String> request = new HttpEntity<>(headers);
        String url = generateUrl("/api/prediccion/especifica/municipio/horaria/"+convertirCodigo(codigoMunicipio));
        ResponseEntity<PrediccionAemetResponse> result = restTemplate.exchange(url, HttpMethod.GET, request, PrediccionAemetResponse.class);
        if (result.getBody() != null && result.getBody().getEstado() == SUCCESS_CODE)
            return getPrediccionData(result.getBody().getDatos(),unidad);

        return null;
    }

    private String generateUrl(String path) {
        return aemetBaseUrl + path;
    }

    private String convertirCodigo(String codigoMunicipio) {
        // Conversión de codigo de municipio en formato String:"idXXXXX" a String:"XXXXX"
        String[] codigos = codigoMunicipio.split("id");
        if (codigos[1] != null) return codigos[1];
        return "";
    }

    private PrediccionResponse getPrediccionData(String urlData,String unidad) {
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<PrediccionData[]> result = restTemplate.exchange(urlData, HttpMethod.GET, request, PrediccionData[].class);
        return mapPrediccionDataToPrediccionResponse(result.getBody(),unidad);
    }

    private PrediccionResponse mapPrediccionDataToPrediccionResponse(PrediccionData[] predicciones,String unidad) {
        // predicciones[0].prediccion.dia[1] == DIA DE MAÑANA
        // Si no viniera siempre en esa posición se podría calcular por el campo fecha
        if (predicciones != null && predicciones.length > 0
                && predicciones[0].getPrediccion() != null &&
                predicciones[0].getPrediccion().getDia() != null &&
                predicciones[0].getPrediccion().getDia().size() > 1 &&
                predicciones[0].getPrediccion().getDia(1) != null) {
            Dium tomorrow = predicciones[0].getPrediccion().getDia(1);
            return new PrediccionResponse(ajustarTemperaturaPorUnidad(tomorrow.calcularTemperaturaMedia(),unidad),unidad,tomorrow.getProbPrecipitacion());
        }
        return null;
    }

     private float ajustarTemperaturaPorUnidad(float temperatura,String unidad) {
        return temperatura+UNIDADES_TEMPERATURA.get(unidad);
     }

}
