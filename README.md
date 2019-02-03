<h2>顺风车</h2>

ssl
server {
   listen 443 ssl;
   listen [::]:443 ssl;
   server_name www.ctdc.xyz;
   ssl on;
   ssl_certificate      /etc/nginx/ssl/1_www.ctdc.xyz_bundle.crt;
   ssl_certificate_key  /etc/nginx/ssl/2_www.ctdc.xyz.key;

   ssl_session_timeout 5m;
   ssl_protocols TLSv1 TLSv1.1 TLSv1.2;
   ssl_ciphers ECDHE-RSA-AES128-GCM-SHA256:HIGH:!aNULL:!MD5:!RC4:!DHE;
   ssl_prefer_server_ciphers on;

   location / {
   proxy_pass http://127.0.0.1:8080;                                      #这个一定要是https
   proxy_next_upstream error timeout invalid_header http_500 http_502 http_503; 
   proxy_set_header       Host $host;  
   proxy_set_header  X-Real-IP  $remote_addr;  
   proxy_set_header  X-Forwarded-For $proxy_add_x_forwarded_for;  
   proxy_set_header X-Forwarded-Proto  $scheme; 
   proxy_redirect off; 
}
}