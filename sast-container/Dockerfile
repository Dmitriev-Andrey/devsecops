FROM openjdk:11

RUN mkdir sast \
    && cd sast \
    && wget https://github.com/find-sec-bugs/find-sec-bugs/releases/download/version-1.11.0/findsecbugs-cli-1.11.0.zip \
    && unzip findsecbugs-cli-1.11.0.zip \
    && rm findsecbugs-cli-1.11.0.zip \
# fix problem for linux
    && sed -i 's/\r$//' findsecbugs.sh \
    && chmod +x findsecbugs.sh

