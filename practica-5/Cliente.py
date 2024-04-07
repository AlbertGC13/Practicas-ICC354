import asyncio
import websockets
import json
import random
import datetime

async def send_sensor_data(id_dispositivo, uri):
    async with websockets.connect(uri) as websocket:
        while True:
            sensor_data = {
                "fechaGeneracion": datetime.datetime.now().strftime("%d/%m/%Y %H:%M:%S"),
                "IdDispositivo": id_dispositivo,
                "temperatura": round(random.uniform(20, 30), 2),
                "humedad": round(random.uniform(30, 50), 2)
            }
            
            await websocket.send(json.dumps(sensor_data))
            await asyncio.sleep(1)

async def main():
    uri = "ws://localhost:8080"  # Reemplaza con la URL y el puerto correctos
    await asyncio.gather(
        send_sensor_data(1, uri),
        send_sensor_data(2, uri)
    )

# Inicia el bucle de eventos
asyncio.run(main())
