# Configure the AWS Provider
provider "aws" {
  region = "ap-south-1"
}

resource "aws_instance" "server1" {

    ami = "ami-0e35ddab05955cf57"
    instance_type = "t2.micro"

}

#resource "aws_vpc" "vpc1" {
#    cidr_block = "10.0.0.0/16"
#    tags = {
#        Name = "production"
#    }
#}
#
#resource "aws_subnet" "subnet1" {
#
#    vpc_id = aws_vpc.vpc1.id
#    cidr_block = "10.0.0.0/24"
#
#    tags = {
#
#        Name = "prod-subnet"
#    }
#
#}
