package com.aliyun.oss.demo;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.List;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.BucketInfo;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;

/*
	 * OSS四大属性：
	 * endpoint ：OSS域名
	 * accessKsyID：访问ID
	 * accessKeySecret:访问密钥
	 * BucketName:Bucket是用来管理Object的存储空间
	 */
public class OssTest {
	private static String endpoint="https://oss-cn-shenzhen.aliyuncs.com";
	private static String accessKeyId="";
	private static String accessKeySecret="";
	private static String bucketName="niududu-new";
	
	public static void main(String[] args) {
		//生成OSSClient对象
		OSSClient ossClient=new OSSClient(endpoint, accessKeyId, accessKeySecret);
		//健壮性判断是否存在该Bucket对象
		if(ossClient.doesBucketExist(bucketName)) {
			System.out.println("该存储空间"+bucketName+"已存在");
		}else {
			System.out.println("该存储空间"+bucketName+"不存在，现在创建。");
			ossClient.createBucket(bucketName);
		}
		BucketInfo bucketInfo = ossClient.getBucketInfo(bucketName);
		System.out.println("Bucket："+bucketName+"信息如下");
		System.out.println(bucketInfo.getBucket().getCreationDate());
		System.out.println(bucketInfo.getBucket().getName());
		System.out.println(bucketInfo.getBucket().getLocation());
		System.out.println(bucketInfo.getBucket().toString());
		
		//上传图片文件的命名：
		String  fileKey ="mobliePay1.jpg";
//		final String keySuffixWithSlash="parent_directory/";
//		ossClient.putObject(bucketName, keySuffixWithSlash, new ByteArrayInputStream(new byte[0]));
		//从本地文件上传new File("本地文件路径")传
		ossClient.putObject(bucketName,fileKey, new File("C://Users//pc//Desktop//官网图片/132.jpg"));
		 System.out.println("Object：" + fileKey + "存入OSS成功。");
		 
		 ObjectListing objectListing = ossClient.listObjects(bucketName);
         List<OSSObjectSummary> objectSummary = objectListing.getObjectSummaries();
         System.out.println("您有以下Object：");
         for (OSSObjectSummary object : objectSummary) {
             System.out.println("\t" + object.getKey());
         }
         ossClient.deleteBucket("<bucketName>");
         ossClient.shutdown();
	}
	

}
