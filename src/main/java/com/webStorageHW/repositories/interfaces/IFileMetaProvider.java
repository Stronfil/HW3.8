package com.webStorageHW.repositories.interfaces;

import com.webStorageHW.entites.FileMetaDTO;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;

public interface IFileMetaProvider {
    String checkFileExists(UUID fileHash);

    void saveFileMeta(UUID Hash, String fileName, int subType, Date date, int fileSize);

    Collection<FileMetaDTO> getMetaFiles(int subType);
}
