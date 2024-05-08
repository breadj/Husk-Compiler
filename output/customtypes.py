def _init_TypeName(id1, id2, id3):
	return { "id1":id1, "id2":id2, "id3":id3 }

obj = _init_TypeName(15.0, "scranner", "h")
typeVar = obj["id1"]
print("billy " + obj["id2"])
def _init_Person(name, age):
	return { "name":name, "age":age }

def _init_Child(name, age, mother, father):
	return { "name":name, "age":age, "mother":mother, "father":father }

unknownMother = _init_Person("Anastasia", 72.0 / 3.0)
katya = None
yuri = _init_Person("Yu" + "ri", 23.0)
anastasia = unknownMother
katya = _init_Child("Katya", 3.0, anastasia, yuri)
print("Katya's father is called " + katya["father"]["name"])
