FROM node:16

WORKDIR /react

COPY package*.json ./

RUN npm install

COPY . .

EXPOSE 9000

RUN npm run build

CMD ["npm", "run", "start"]
