import random

names = ["Art","Biology","Chemistry",
         "DT","ECL","English",
         "History","Math","PE",
         "Physics","Science","Spanish",
         "Philosphy","Geography","Politics",
         "Economy","I.C.T.","Robotics",]
colors = ["#f44336", "#e91e63", "#9c27b0",
          "#673ab7","#3f51b5","#2196f3",
          "#03a9f4","#00bcd4","#009688",
          "#4caf50","#8bc34a","#cddc39",
          "#ffeb3b","#ffc107","#ff9800",
          "#ff5722","#795548","#607d8b"]
def generate():
    randName = names[random.randint(0,len(names)-1)]

    randColor = colors[random.randint(0,len(colors)-1)]

    randPages = random.randint(1,99)

    randDay = random.randint(1,30)
    randMonth = random.randint(1,12)

    if 1 <= randDay <= 9:
        randDay = "0%s" % randDay
    else:
        randDay = "%s" % randDay

    if 1 <= randMonth <= 9:
        randMonth = "0%s" % randMonth
    else:
        randMonth = "%s" % randMonth
    return "a = new Notebook(\"%s\",\"%s\", %s, Helpers.stringDataToMillis(\"2016/%s/%s\"));list.add(a);" % (randName,randColor,randPages,randMonth,randDay)
#generate()
#goOn = raw_input("\nAnother number? write x to quit:  ")
while True:
    for x in range (0,15):
        output = []
        output.append(generate())
        print '\n'.join(output)
        
    goOn = raw_input("\nAnother number? write x to quit:  ")
    if goOn == 'x':
        break
