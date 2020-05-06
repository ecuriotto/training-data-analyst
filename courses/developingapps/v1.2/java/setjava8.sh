echo "Installing java 8"
sudo apt-get update
sudo apt-get --assume-yes install openjdk-8-jdk
export JAVA_HOME=/usr/lib/jvm/java-1.8.0-openjdk-amd64
echo "Running update-alternatives to set java 8 instead of 11"
sudo update-alternatives --install /usr/bin/java java /usr/lib/jvm/java-8-openjdk-amd64/jre/bin/java 2
java -version
echo $JAVA_HOME