/**
 * ʾ��˵��
 * 
 * HelloOSS��OSS Java SDK��ʾ�������������޸�endpoint��accessKeyId��accessKeySecret��bucketName��ֱ�����С�
 * ���з�����ο�README��
 * 
 * ��ʾ���еĲ�������OSS Java SDK�����й��ܣ���ϸ���ܼ�ʹ�÷�������ο���SDK�ֲ� > Java-SDK����
 * ���ӵ�ַ�ǣ�https://help.aliyun.com/document_detail/oss/sdk/java-sdk/preface.html?spm=5176.docoss/sdk/java-sdk/��
 * 
 * ����OSS Java SDK�ķ���ʱ���׳��쳣��ʾ�д�������û���׳��쳣��ʾ�ɹ�ִ�С�
 * ��������ʱ��OSS Java SDK�ķ������׳��쳣���쳣�а��������롢������Ϣ����ϸ��ο���SDK�ֲ� > Java-SDK > �쳣������
 * ���ӵ�ַ�ǣ�https://help.aliyun.com/document_detail/oss/sdk/java-sdk/exception.html?spm=5176.docoss/api-reference/error-response��
 * 
 * OSS����̨����ֱ�۵Ŀ���������OSS Java SDK�Ľ����OSS����̨��ַ�ǣ�https://oss.console.aliyun.com/index#/��
 * OSS����̨ʹ�÷�����ο��ĵ����ĵġ�����̨�û�ָ�ϡ��� ָ�ϵ������ӵ�ַ�ǣ�https://help.aliyun.com/document_detail/oss/getting-started/get-started.html?spm=5176.docoss/user_guide��
 * 
 * OSS���ĵ����ĵ�ַ�ǣ�https://help.aliyun.com/document_detail/oss/user_guide/overview.html��
 * OSS Java SDK���ĵ���ַ�ǣ�https://help.aliyun.com/document_detail/oss/sdk/java-sdk/install.html?spm=5176.docoss/sdk/java-sdk��
 * 
 */

package com.aliyun.oss.demo;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.BucketInfo;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class test {
    static Logger logger = Logger.getLogger(test.class);

    // endpoint�Ƿ���OSS��������������Ѿ���OSS�Ŀ���̨�� ������Bucket�����ڿ���̨�ϲ鿴������
    // �������û�д���Bucket��endpointѡ����ο��ĵ����ĵġ�������Աָ�� > �������� > ������������
    // ���ӵ�ַ�ǣ�https://help.aliyun.com/document_detail/oss/user_guide/oss_concept/endpoint.html?spm=5176.docoss/user_guide/endpoint_region
    // endpoint�ĸ�ʽ���硰http://oss-cn-hangzhou.aliyuncs.com/����ע��http://�󲻴�bucket���ƣ�
    // ���硰http://bucket-name.oss-cn-hangzhou.aliyuncs.com�����Ǵ����endpoint����ȥ�����еġ�bucket-name����
    private static String endpoint = "https://oss-cn-shenzhen.aliyuncs.com";

    // accessKeyId��accessKeySecret��OSS�ķ�����Կ���������ڿ���̨�ϴ����Ͳ鿴��
    // �����Ͳ鿴������Կ�����ӵ�ַ�ǣ�https://ak-console.aliyun.com/#/��
    // ע�⣺accessKeyId��accessKeySecretǰ��û�пո񣬴ӿ���̨����ʱ���鲢ȥ������Ŀո�
    private static String accessKeyId = "LTAIfTVYN8w5mmi1";
    private static String accessKeySecret = "fDLgyulAXElgA8IZXPuosidGbnvP4f";

    // Bucket�����������洢Object�Ĵ洢�ռ䣬��ϸ������ο���������Աָ�� > �������� > OSS����������ܡ���
    // Bucket�����淶���£�ֻ�ܰ���Сд��ĸ�����ֺͶ̺��ߣ�-����������Сд��ĸ�������ֿ�ͷ�����ȱ�����3-63�ֽ�֮�䡣
    private static String bucketName = "vmware-niududu";

    // Object��OSS�洢���ݵĻ�����Ԫ����ΪOSS�Ķ���Ҳ����ΪOSS���ļ�����ϸ������ο���������Աָ�� > �������� > OSS����������ܡ���
    // Object�����淶���£�ʹ��UTF-8���룬���ȱ�����1-1023�ֽ�֮�䣬�����ԡ�/�����ߡ�\���ַ���ͷ��
    private static String firstKey = "test1.txt";

    public static void main(String[] args) {

        // ��־���ã�OSS Java SDKʹ��log4j��¼������Ϣ��ʾ��������ڹ���Ŀ¼�����ɡ�oss-demo.log����־�ļ���Ĭ����־������INFO��
        // ��־�������ļ��ǡ�conf/log4j.properties�������������Ҫ��־������û����־�����ļ����������־���á�
        PropertyConfigurator.configure("D://java//eclipse-jee-oxygen-1a-win32-x86_64//eclipse-workspace//OSS//src//main//resources//log4j.properties");

        logger.info("Started");

        // ����OSSClient��������ָ��һЩ�����������SDK�ֲ� > Java-SDK > ��ʼ������
        // ���ӵ�ַ�ǣ�https://help.aliyun.com/document_detail/oss/sdk/java-sdk/init.html?spm=5176.docoss/sdk/java-sdk/get-start
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

        try {

            // �ж�Bucket�Ƿ���ڡ���ϸ��ο���SDK�ֲ� > Java-SDK > ����Bucket����
            // ���ӵ�ַ�ǣ�https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_bucket.html?spm=5176.docoss/sdk/java-sdk/init
            if (ossClient.doesBucketExist(bucketName)) {
                System.out.println("���Ѿ�����Bucket��" + bucketName + "��");
            } else {
                System.out.println("����Bucket�����ڣ�����Bucket��" + bucketName + "��");
                // ����Bucket����ϸ��ο���SDK�ֲ� > Java-SDK > ����Bucket����
                // ���ӵ�ַ�ǣ�https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_bucket.html?spm=5176.docoss/sdk/java-sdk/init
                ossClient.createBucket(bucketName);
            }

            // �鿴Bucket��Ϣ����ϸ��ο���SDK�ֲ� > Java-SDK > ����Bucket����
            // ���ӵ�ַ�ǣ�https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_bucket.html?spm=5176.docoss/sdk/java-sdk/init
            BucketInfo info = ossClient.getBucketInfo(bucketName);
            System.out.println("Bucket " + bucketName + "����Ϣ���£�");
            System.out.println("\t�������ģ�" + info.getBucket().getLocation());
            System.out.println("\t����ʱ�䣺" + info.getBucket().getCreationDate());
            System.out.println("\t�û���־��" + info.getBucket().getOwner());

            //��ȡ�ļ�
//            File file=new File("H://oss-img/gintoki.jpg");
//            @SuppressWarnings("resource")
//			FileInputStream input=new FileInputStream(file);
//            FileChannel channel=input.getChannel();
//            ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size()); 
            // ���ַ�������OSS��Object������ΪfirstKey����ϸ��ο���SDK�ֲ� > Java-SDK > �ϴ��ļ�����
            // ���ӵ�ַ�ǣ�https://help.aliyun.com/document_detail/oss/sdk/java-sdk/upload_object.html?spm=5176.docoss/user_guide/upload_object
//           InputStream is = new ByteArrayInputStream(byteBuffer.array());
            InputStream is = new ByteArrayInputStream("test".getBytes());
            ossClient.putObject(bucketName, firstKey, is);
            System.out.println("Object��" + firstKey + "����OSS�ɹ���");

            // �����ļ�����ϸ��ο���SDK�ֲ� > Java-SDK > �����ļ�����
            // ���ӵ�ַ�ǣ�https://help.aliyun.com/document_detail/oss/sdk/java-sdk/download_object.html?spm=5176.docoss/sdk/java-sdk/manage_object
            OSSObject ossObject = ossClient.getObject(bucketName, firstKey);
            InputStream inputStream = ossObject.getObjectContent();
            StringBuilder objectContent = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            while (true) {
                String line = reader.readLine();
                if (line == null)
                    break;
                objectContent.append(line);
            }
            
            inputStream.close();
            System.out.println("Object��" + firstKey + "�������ǣ�" + objectContent);

            // �ļ��洢��OSS��Object������ΪfileKey����ϸ��ο���SDK�ֲ� > Java-SDK > �ϴ��ļ�����
            // ���ӵ�ַ�ǣ�https://help.aliyun.com/document_detail/oss/sdk/java-sdk/upload_object.html?spm=5176.docoss/user_guide/upload_object
            String fileKey = "132.jpg";
            ossClient.putObject(bucketName, fileKey,new File("C://Users//pc//Desktop//����ͼƬ/132.jpg"));
            System.out.println("Object��" + fileKey + "����OSS�ɹ���");

            // �鿴Bucket�е�Object����ϸ��ο���SDK�ֲ� > Java-SDK > �����ļ�����
            // ���ӵ�ַ�ǣ�https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_object.html?spm=5176.docoss/sdk/java-sdk/manage_bucket
            ObjectListing objectListing = ossClient.listObjects(bucketName);
            List<OSSObjectSummary> objectSummary = objectListing.getObjectSummaries();
            System.out.println("��������Object��");
            for (OSSObjectSummary object : objectSummary) {
                System.out.println("\t" + object.getKey());
            }

            // ɾ��Object����ϸ��ο���SDK�ֲ� > Java-SDK > �����ļ�����
            // ���ӵ�ַ�ǣ�https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_object.html?spm=5176.docoss/sdk/java-sdk/manage_bucket
 //           ossClient.deleteObject(bucketName, firstKey);
            System.out.println("ɾ��Object��" + firstKey + "�ɹ���");
 //           ossClient.deleteObject(bucketName, fileKey);
            System.out.println("ɾ��Object��" + fileKey + "�ɹ���");
            OSSObject object = ossClient.getObject(bucketName,fileKey);
   
        } catch (OSSException oe) {
            oe.printStackTrace();
        } catch (ClientException ce) {
            ce.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ossClient.shutdown();
        }

        logger.info("Completed");
    }

}
