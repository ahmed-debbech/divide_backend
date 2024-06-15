package com.debbech.divide.processor.scanner;

import org.json.JSONObject;

import java.io.File;

public interface IScanner {
    JSONObject execute(String imagePath) throws Exception;
}
