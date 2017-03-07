if __name__ == '__main__':
    with open('prepopulate.sql', 'w') as outfile:
    	for i in range(20):
    		q1 = 'INSERT INTO Customer (Customer_Name, Phone_Number, Street, City, State, Zip_Code) VALUES ("Customer' + str(i) + '", "1234567890", "1 Grand Ave", "San Luis Obispo", "CA", "93405");\n' 
    		outfile.write(q1)

    	outfile.write('\n')

    	for i in range(20):
    		q1 = 'INSERT INTO Vendor (Vender_Name, Phone_Number, Street, City, State, Zip_Code) VALUES ("Vendor' + str(i) + '", "0987654321", "2 Grand Ave", "San Luis Obispo", "CA", "93405");\n'
    		outfile.write(q1)

    	outfile.write('\n')

    	for i in range(20):
    		q1 = 'INSERT INTO Textbook (ISBN, Title, Subject, Author, Edition, Price) VALUES ("' + str(i) + '", "Title' + str(i) + '", "Subject' + str(i) + '", "Author' + str(i) + '", ' + str(i) + ', 50.00);\n'
    		outfile.write(q1)

    	outfile.write('\n')

    	for i in range(20):
    		q1 = 'INSERT INTO Professor (Professor_Name, Email, Department) VALUES ("Professor' + str(i) + '", "Email' + str(i) + '", "Department' + str(i) + '");\n'
    		outfile.write(q1)

    	outfile.write('\n')

    	for i in range(20):
    		q1 = 'INSERT INTO Course (Department, Course_Number, Professor_ID) VALUES ("Department' + str(i) + '", ' + str(i) + ', ' + str(i + 1) + ');\n'
    		outfile.write(q1)

    	outfile.write('\n')

    	for i in range(20):
    		q1 = 'INSERT INTO Transaction (Vendor_ID, Customer_ID, ISBN, Purchase_Date) VALUES (' + str(i + 1) + ', ' + str(i + 1) + ', ' + str(i) + ', "2017-03-05");\n'
    		outfile.write(q1)

    	outfile.write('\n')

    	for i in range(20):
    		q1 = 'INSERT INTO RequiredBook (Department, Course_Number, Professor_ID, Textbook_Required) VALUES ("Department' + str(i) + '", ' + str(i) + ', ' + str(i + 1) + ', ' + str(i) + ');\n'
    		outfile.write(q1)