package com.example.harsh.aide;

import java.io.IOException;

/**
 * Created by KOUSHIK on 07-03-2017.
 */

public interface ItemClickCallback {
    void OnItemClick(int id);
    void OnSubItemClick(int id) throws IOException;
}
