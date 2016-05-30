package com.prempal.crypt;

/**
 * Created by prempal on 31/5/16.
 */
public interface NetworkInterface {

    void fetchDirectoryListing();

    void downloadFile(String fileName);

    void updateCurrentPath(String folder);

}
