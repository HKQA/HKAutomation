#!/bin/bash

#today='date +"%Y-%m-%d"'

yesterday=`date -d 'yesterday' +%Y-%m-%d`
echo $yesterday

cd /home/release/reports/
path=`pwd`
echo $path

rm -f /home/release/reports/report/*.xls
echo "deleting previous reports"

#total
mysql -uhkadmin -padmin2K11! hkpay -h 192.168.70.28 -e "SELECT HOUR(ps.payment_date),istp.display_name,COUNT(*) FROM payment_request pr JOIN payment_response ps ON ps.payment_request_id = pr.id JOIN issuer iss ON pr.issuer_id  = iss.id JOIN issuer_type istp ON iss.issuer_type_id = istp.id WHERE  ps.payment_status_id='3' AND pr.sent_to_nodal_account=1 AND DATE(ps.payment_date) = '$yesterday' GROUP BY 1,2 ORDER BY 1;" >> /home/release/reports/report/Total_nodal.xls

mysql -uhkadmin -padmin2K11! hkpay -h 192.168.70.28 -e "SELECT HOUR(ps.payment_date),istp.display_name,COUNT(*) FROM payment_request pr JOIN payment_response ps ON ps.payment_request_id = pr.id JOIN issuer iss ON pr.issuer_id  = iss.id JOIN issuer_type istp ON iss.issuer_type_id = istp.id WHERE  ps.payment_status_id='3' AND pr.sent_to_nodal_account=0 AND DATE(ps.payment_date) = '$yesterday' GROUP BY 1,2 ORDER BY 1;" >> /home/release/reports/report/Total_non_nodal.xls

#Android
mysql -uhkadmin -padmin2K11! hkpay -h 192.168.70.28 -e "SELECT HOUR(ps.payment_date),istp.display_name,COUNT(*) FROM payment_request pr JOIN payment_response ps ON ps.payment_request_id = pr.id JOIN issuer iss ON pr.issuer_id  = iss.id JOIN issuer_type istp ON iss.issuer_type_id = istp.id WHERE  ps.payment_status_id='3' AND pr.sent_to_nodal_account=1 AND DATE(ps.payment_date) = '$yesterday' and pr.order_id like 'HKA%' GROUP BY 1,2 ORDER BY 1;" >> /home/release/reports/report/Android_nodal.xls

mysql -uhkadmin -padmin2K11! hkpay -h 192.168.70.28 -e "SELECT HOUR(ps.payment_date),istp.display_name,COUNT(*) FROM payment_request pr JOIN payment_response ps ON ps.payment_request_id = pr.id JOIN issuer iss ON pr.issuer_id  = iss.id JOIN issuer_type istp ON iss.issuer_type_id = istp.id WHERE  ps.payment_status_id='3' AND pr.sent_to_nodal_account=0 AND DATE(ps.payment_date) = '$yesterday' and pr.order_id like 'HKA%' GROUP BY 1,2 ORDER BY 1;" >> /home/release/reports/report/Android_non_nodal.xls

#IOS
mysql -uhkadmin -padmin2K11! hkpay -h 192.168.70.28 -e "SELECT HOUR(ps.payment_date),istp.display_name,COUNT(*) FROM payment_request pr JOIN payment_response ps ON ps.payment_request_id = pr.id JOIN issuer iss ON pr.issuer_id  = iss.id JOIN issuer_type istp ON iss.issuer_type_id = istp.id WHERE  ps.payment_status_id='3' AND pr.sent_to_nodal_account=1 AND DATE(ps.payment_date) = '$yesterday' and pr.order_id like 'HKI%' GROUP BY 1,2 ORDER BY 1;" >> /home/release/reports/report/IOS_nodal.xls

mysql -uhkadmin -padmin2K11! hkpay -h 192.168.70.28 -e "SELECT HOUR(ps.payment_date),istp.display_name,COUNT(*) FROM payment_request pr JOIN payment_response ps ON ps.payment_request_id = pr.id JOIN issuer iss ON pr.issuer_id  = iss.id JOIN issuer_type istp ON iss.issuer_type_id = istp.id WHERE  ps.payment_status_id='3' AND pr.sent_to_nodal_account=0 AND DATE(ps.payment_date) = '$yesterday' and pr.order_id like 'HKI%' GROUP BY 1,2 ORDER BY 1;" >> /home/release/reports/report/IOS_non_nodal.xls

#Desktop
mysql -uhkadmin -padmin2K11! hkpay -h 192.168.70.28 -e "SELECT HOUR(ps.payment_date),istp.display_name,COUNT(*) FROM payment_request pr JOIN payment_response ps ON ps.payment_request_id = pr.id JOIN issuer iss ON pr.issuer_id  = iss.id JOIN issuer_type istp ON iss.issuer_type_id = istp.id WHERE  ps.payment_status_id='3' AND pr.sent_to_nodal_account=1 AND DATE(ps.payment_date) = '$yesterday' and pr.order_id like 'HKD%' GROUP BY 1,2 ORDER BY 1;" >> /home/release/reports/report/Desktop_nodal.xls

mysql -uhkadmin -padmin2K11! hkpay -h 192.168.70.28 -e "SELECT HOUR(ps.payment_date),istp.display_name,COUNT(*) FROM payment_request pr JOIN payment_response ps ON ps.payment_request_id = pr.id JOIN issuer iss ON pr.issuer_id  = iss.id JOIN issuer_type istp ON iss.issuer_type_id = istp.id WHERE  ps.payment_status_id='3' AND pr.sent_to_nodal_account=0 AND DATE(ps.payment_date) = '$yesterday' and pr.order_id like 'HKD%' GROUP BY 1,2 ORDER BY 1;" >> /home/release/reports/report/Desktop_non_nodal.xls

#MobileWeb
mysql -uhkadmin -padmin2K11! hkpay -h 192.168.70.28 -e "SELECT HOUR(ps.payment_date),istp.display_name,COUNT(*) FROM payment_request pr JOIN payment_response ps ON ps.payment_request_id = pr.id JOIN issuer iss ON pr.issuer_id  = iss.id JOIN issuer_type istp ON iss.issuer_type_id = istp.id WHERE  ps.payment_status_id='3' AND pr.sent_to_nodal_account=1 AND DATE(ps.payment_date) = '$yesterday' and pr.order_id like 'HKM%' GROUP BY 1,2 ORDER BY 1;" >>/home/release/reports/report/MobileWeb_nodal.xls

mysql -uhkadmin -padmin2K11! hkpay -h 192.168.70.28 -e "SELECT HOUR(ps.payment_date),istp.display_name,COUNT(*) FROM payment_request pr JOIN payment_response ps ON ps.payment_request_id = pr.id JOIN issuer iss ON pr.issuer_id  = iss.id JOIN issuer_type istp ON iss.issuer_type_id = istp.id WHERE  ps.payment_status_id='3' AND pr.sent_to_nodal_account=0 AND DATE(ps.payment_date) = '$yesterday' and pr.order_id like 'HKM%' GROUP BY 1,2 ORDER BY 1;" >>/home/release/reports/report/MobileWeb_non_nodal.xls


attachment="/home/release/reports/report/Total_nodal.xls"
attachment1="/home/release/reports/report/Android_nodal.xls"
attachment2="/home/release/reports/report/IOS_nodal.xls"
attachment3="/home/release/reports/report/Desktop_nodal.xls"
attachment4="/home/release/reports/report/MobileWeb_nodal.xls"
attachment5="/home/release/reports/report/IOS_non_nodal.xls"
attachment6="/home/release/reports/report/Desktop_non_nodal.xls"
attachment7="/home/release/reports/report/MobileWeb_non_nodal.xls"
attachment8="/home/release/reports/report/Android_non_nodal.xls"
attachment9="/home/release/reports/report/Total_non_nodal.xls"

email="hulk@brightlifecare.com"

echo -e "Mailing the Reports to $email"
cat /home/release/reports/description_payment_order.txt | mail -s "Order placement Report for different platform and payment mode " -a $attachment -a $attachment1 -a $attachment2 -a $attachment3 -a $attachment4 -a $attachment5 -a $attachment6 -a $attachment7 -a $attachment8 -a $attachment9 $email
