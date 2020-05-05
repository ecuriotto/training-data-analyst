/*
 * Copyright 2018 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.training.appdev.services.gcp.cloudstorage;

// TODO: Write a star import for Cloud Storage
import com.google.cloud.storage.*;


// END TODO

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

@Service
public class ImageService {

// TODO: Create the storage client
// The StorageOptions class has a getDefaultInstance()
// static method.
// Use the getService() method to get the storage client
private static Storage storage = StorageOptions
        .getDefaultInstance()
        .getService();

// END TODO

// TODO: Get the name of the Cloud Storage bucket
// Use a Spring @Value annotation to get the value
// Get the value using ${google.storage.bucket}
// This references the GCLOUD_BUCKET environment variable

    @Value("${google.storage.bucket}")
    private String bucketName;

// END TODO


    public String saveImage(MultipartFile file) throws IOException {
    // The existing code in the method creates a unique name
    // based on the file's original name. It has a 
    // prefix generated using the current date and time.
    // This should ensure that a new file upload won't 
    // overwrite an existing object in the bucket
        String fileName = System.nanoTime() + file.getOriginalFilename();

    // TODO: Create a new Cloud Storage object
    // Use the BlobInfo class to represent this object
    // Use the BlobInfo.Builder to customize the Blob
    // Set the content type from the file
    // Set the object ACL to Public Read
        BlobInfo blobInfo = storage.create(
                BlobInfo.newBuilder(bucketName, fileName)
                        .setContentType(file.getContentType())
                        .setAcl(new ArrayList<>(
                                Arrays.asList(Acl.of(Acl.User.ofAllUsers(),
                                        Acl.Role.READER))))
                        .build(),
                file.getInputStream());


    // END TODO

    // TODO: Cloud Storage public URLs are in the form:
    // https://storage.googleapis.com/[BUCKET]/[OBJECT]
    // Use String concatentation to create return the URL
        
    return "https://storage-download.googleapis.com/" + bucketName+ "/" +fileName;

    // END TODO

    }

}