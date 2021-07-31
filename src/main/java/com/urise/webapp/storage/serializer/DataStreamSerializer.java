package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            writeCollection(dos, contacts.entrySet(), entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });
            writeCollection(dos, r.getSections().entrySet(), entry -> {
                SectionType type = entry.getKey();
                Section section = entry.getValue();
                dos.writeUTF(type.name());
                switch (type) {
                    case PERSONAL, OBJECTIVE -> dos.writeUTF(((TextSection) section).getContent());
                    case ACHIEVEMENT, QUALIFICATIONS -> writeCollection(dos, ((ListSection) section).getItems(), dos::writeUTF);
                    case EXPERIENCE, EDUCATION -> writeCollection(dos, ((OrganizationSection) section).getOrganizations(), org -> {
                        dos.writeUTF(org.getHomePage().getName());
                        dos.writeUTF(org.getHomePage().getUrl());
                        writeCollection(dos, org.getPositions(), position -> {
                            writeLocaleDate(dos, position.getStartDate());
                            writeLocaleDate(dos, position.getEndDate());
                            dos.writeUTF(position.getTitle());
                            dos.writeUTF(position.getDescription());
                        });
                    });
                }
            });
        }
    }

    private void writeLocaleDate(DataOutputStream dos, LocalDate ld) throws IOException {
        dos.writeInt(ld.getYear());
        dos.writeInt(ld.getMonth().getValue());
    }

    private LocalDate readLocaleDate(DataInputStream dis) throws IOException {
        return LocalDate.of(dis.readInt(), dis.readInt(), 1);
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            readItems(dis, () -> {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            });
            readItems(dis, () -> {
                SectionType type = SectionType.valueOf(dis.readUTF());
                resume.addSection(type, readSection(dis, type));
            });
            return resume;
        }
    }

    private Section readSection(DataInputStream dis, SectionType type) throws IOException {
        switch (type) {
            case PERSONAL, OBJECTIVE:
                return new TextSection(dis.readUTF());
            case ACHIEVEMENT, QUALIFICATIONS:
                return new ListSection(readList(dis, dis::readUTF));
            case EXPERIENCE, EDUCATION:
                return new OrganizationSection(readList(dis, () -> new Organization(
                        new Link(dis.readUTF(), dis.readUTF()),
                            readList(dis, ()-> new Organization.Position(readLocaleDate(dis), readLocaleDate(dis),dis.readUTF(), dis.readUTF())))));
            default:
                throw new IllegalStateException();
        }
    }

    public <T> List<T> readList(DataInputStream dis, ElementReader<T> reader) throws IOException {
        int size = dis.readInt();
        List<T> list = new ArrayList<T>(size);
        for (int i = 0; i < size; i++) {
            list.add(reader.read());
        }
        return list;
    }

    private interface ElementProcessor {
        void process() throws IOException;
    }

    private interface ElementReader<T> {
        T read() throws IOException;
    }

    private interface ElementWriter<T> {
        void write(T t) throws IOException;
    }

    private void readItems(DataInputStream dis, ElementProcessor processor) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            processor.process();
        }

    }

    private <T> void writeCollection(DataOutputStream dos, Collection<T> collection, ElementWriter<T> writer) throws IOException {
        dos.writeInt(collection.size());
        for (T item : collection) {
            writer.write(item);
        }
    }
}
