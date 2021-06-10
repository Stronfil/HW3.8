package com.webStorageHW.repositories;

import com.webStorageHW.entites.FileMetaDTO;
import com.webStorageHW.repositories.interfaces.IFileMetaProvider;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;

@Repository
public class FileMetaProvider implements IFileMetaProvider {

    private static final String GET_FILES_META = "select hash, file_name as filename,date_added as dateAdded, " +
            "file_size as fileSize from store.file_info_metadata " +
            "where sub_type =:subtype";

    private static final String GET_FILE_PATH_BY_HASH = "select file_name as filename from store.file_info_metadata" +
            " where hash =:hash";

    private static final String SAVE_FILE_META_DATA = "insert into store.file_info_metadata (hash, file_name, " +
            "sub_type, date_added, file_size)" + " values (:hash, :file_name, :subtype, :date_added, :file_size)";

    private final Sql2o sql2o;

    public FileMetaProvider(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public String checkFileExists(UUID fileHash) {
        try (Connection connection = sql2o.open()) {
            return connection.createQuery(GET_FILE_PATH_BY_HASH, false)
                    .addParameter("hash", fileHash.toString())
                    .executeScalar(String.class);
        }
    }

    @Override
    public void saveFileMeta(UUID Hash, String fileName, int subType, Date date, int fileSize) {
        try (Connection connection = sql2o.open()) {
            connection.createQuery(SAVE_FILE_META_DATA, false)
                    .addParameter("hash", Hash.toString())
                    .addParameter("file_name", fileName)
                    .addParameter("subtype", subType)
                    .addParameter("date_added", date)
                    .addParameter("file_size", fileSize)
                    .executeUpdate();
        }
    }

    @Override
    public Collection<FileMetaDTO> getMetaFiles(int subType) {
        try (Connection connection = sql2o.open()) {
            return connection.createQuery(GET_FILES_META, false)
                    .addParameter("subtype", subType)
                    .executeAndFetch(FileMetaDTO.class);
        }
    }
}
