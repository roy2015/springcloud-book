1.准备,依次启动 
    1)config（配置服务）
    2) Eureka server
    3) gateway
    4) uaa
    5) user

2. 新注册一个用户  
curl -X POST --header 'Content-Type: application/json' --header 'Accept: application/json' \
-d '{
"password": "123456",
"username": "miya"
}' 'http://localhost:6000/userapi/user/registry'
注册成功，返回改用户信息如下
{"id":9,"username":"miya","password":"$2a$10$F3dEmmqm1.DzAVscRucAPuUfyvKwhg7bvqkxeozf4VPUyamqwCUUe"}

2.登录接口,如果用户名密码正确，会远程调用uaa-service服务获取JWT
curl -X POST --header 'Content-Type: application/json' --header 'Accept: application/json' \
'http://localhost:6000/userapi/user/login?username=miya&password=123456'

登录成功，返回信息如下
{"code":0,"error":"","data":{"user":{"id":9,"username":"miya","password":"$2a$10$F3dEmmqm1.DzAVscRucAPuUfyvKwhg7bvqkxeozf4VPUyamqwCUUe"},"token":"eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2OTY3NDI1NjEsInVzZXJfbmFtZSI6Im1peWEiLCJqdGkiOiJkMWY4NTI2MS1lNGNmLTQzZGUtOTZlNi01ZjNkZDc3N2VmNjMiLCJjbGllbnRfaWQiOiJ1YWEtc2VydmljZSIsInNjb3BlIjpbInNlcnZpY2UiXX0.AnWsi6yw14wGaan3MLRf6p9_g__G17XfVLSGwX0D-RdjXR7eL9X4Gd9kcfwkoeFSn7ssm0VL-DO4NpE8E9ouIOkoxHI6Ov30ZwXUw-rQbefysZc90yjpfYjqoTzXoojZbAAFV0JgsGDr-AauDKzrsbRo-iHMb3eKlx1dqxnHYTgB0A1gmswLZMK-vwy_lvJi0GXbIF_rnvpodx_SUVfzT6Eegi2VaBm2Td7V-gXeYzxx7dx7xzyxIE8QBlzS9XJvCYoWuBXftzIXssbZCMfUPZyaGA-_Edu7ZAPXFkDem-z7v71HtcMIljCx0JxrUsnTDJ_PlodfL68NY7k5Q7KF9w"}}

3. 根据用户名获取用户
   curl -X POST --header 'Content-Type: application/json' --header 'Accept: application/json' \
   --header 'Authorization: Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2OTY3NDQ1MTUsInVzZXJfbmFtZSI6Im1peWEiLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoiNGZmNTdmNTYtZTM4OC00ZGZjLWFlYjktNDNiZDcwZDE5MDI5IiwiY2xpZW50X2lkIjoidWFhLXNlcnZpY2UiLCJzY29wZSI6WyJzZXJ2aWNlIl19.R33bzDQ2g_WAMcfpVVBcTF04hT4RKc8HO0JoO_ZM3dbw3VS4mo_0blYHEHv4AWySeRp9lLKxKjJAM5rftvtMBfIMQBb8CPj03K18w5IrSVBudfFag4KS-lLhoybs4KqVeFWV5u0kdpuhnNqhvA6vAObrAjJUT3zxCyAzDamb-X0Ngy3Ui9SCLEf_F5WFhBIwS4hQcgTFYYWN5cxKKTQmaopAZP-sCbNlyZs5TRSjZnZkM2GApadsbMobLYaORaAnkO4myTICf5AfRdDfzCUxTfuizmOy9VRszPZcxibPzcPIxELnjk_y5mZhcqFkTSNrel_EmuhEFeZJvnJn5DAcnw' \
   'http://localhost:6000/userapi/user/miya'
   返回：
   {"code":0,"error":"","data":{"id":9,"username":"miya","password":"$2a$10$F3dEmmqm1.DzAVscRucAPuUfyvKwhg7bvqkxeozf4VPUyamqwCUUe"}}
   ，需要ROLE_USER权限，如果没有权限，请新增user_role