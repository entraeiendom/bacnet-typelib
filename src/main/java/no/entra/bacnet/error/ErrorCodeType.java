package no.entra.bacnet.error;

import no.entra.bacnet.octet.Octet;

import static java.lang.Integer.parseInt;

public enum ErrorCodeType {
    AbortApduTooLong(123),
    AbortApplicationExceededReplyTime(124),
    AbortBufferOverflow(51),
    AbortInsufficientSecurity(135),
    AbortInvalidApduInThisState(52),
    AbortOther(56),
    AbortOutOfResources(125),
    AbortPreemptedByHigherPriorityTask(53),
    AbortProprietary(55),
    AbortSecurityError(136),
    AbortSegmentationNotSupported(54),
    AbortTsmTimeout(126),
    AbortWindowSizeOutOfRange(127),
    AccessDenied(85),
    AddressingError(115),
    BadDestinationAddress(86),
    BadDestinationDeviceId(87),
    BadSignature(88),
    BadSourceAddress(89),
    BadTimestamp(90),
    Busy(82),
    CannotUseKey(91),
    CannotVerifyMessageId(92),
    CharacterSetNotSupported(41),
    CommunicationDisabled(83),
    ConfigurationInProgress(2),
    CorrectKeyRevision(93),
    CovSubscriptionFailed(43),
    DatatypeNotSupported(47),
    DeleteFdtEntryFailed(120),
    DestinationDeviceIdRequired(94),
    DeviceBusy(3),
    DistributeBroadcastFailed(121),
    DuplicateEntry(137),
    DuplicateMessage(95),
    DuplicateName(48),
    DuplicateObjectId(49),
    DynamicCreationNotSupported(4),
    EncryptionNotConfigured(96),
    EncryptionRequired(97),
    FileAccessDenied(5),
    FileFull(128),
    InconsistentConfiguration(129),
    InconsistentObjectType(130),
    InconsistentParameters(7),
    InconsistentSelectionCriterion(8),
    IncorrectKey(98),
    InternalError(131),
    InvalidArrayIndex(42),
    InvalidConfigurationData(46),
    InvalidDataType(9),
    InvalidEventState(73),
    InvalidFileAccessMethod(10),
    InvalidFileStartPosition(11),
    InvalidKeyData(99),
    InvalidParameterDataType(13),
    InvalidTag(57),
    InvalidTimestamp(14),
    InvalidValueInThisState(138),
    KeyUpdateInProgress(100),
    ListElementNotFound(81),
    LogBufferFull(75),
    LoggedValuePurged(76),
    MalformedMessage(101),
    MessageTooLong(113),
    MissingRequiredParameter(16),
    NetworkDown(58),
    NoAlarmConfigured(74),
    NoObjectsOfSpecifiedType(17),
    NoPropertySpecified(77),
    NoSpaceForObject(18),
    NoSpaceToAddListElement(19),
    NoSpaceToWriteProperty(20),
    NoVtSessionsAvailable(21),
    NotConfigured(132),
    NotConfiguredForTriggeredLogging(78),
    NotCovProperty(44),
    NotKeyServer(102),
    NotRouterToDnet(110),
    ObjectDeletionNotPermitted(23),
    ObjectIdentifierAlreadyExists(24),
    OperationalProblem(25),
    OptionalFunctionalityNotSupported(45),
    Other(0),
    OutOfMemory(133),
    ParameterOutOfRange(80),
    PasswordFailure(26),
    PropertyIsNotAList(22),
    PropertyIsNotAnArray(50),
    ReadAccessDenied(27),
    ReadBdtFailed(117),
    ReadFdtFailed(119),
    RegisterForeignDeviceFailed(118),
    RejectBufferOverflow(59),
    RejectInconsistentParameters(60),
    RejectInvalidParameterDataType(61),
    RejectInvalidTag(62),
    RejectMissingRequiredParameter(63),
    RejectOther(69),
    RejectParameterOutOfRange(64),
    RejectProprietary(68),
    RejectTooManyArguments(65),
    RejectUndefinedEnumeration(66),
    RejectUnrecognizedService(67),
    RouterBusy(111),
    SecurityError(114),
    SecurityNotConfigured(103),
    ServiceRequestDenied(29),
    SourceSecurityRequired(104),
    Success(84),
    Timeout(30),
    TooManyKeys(105),
    UnknownAuthenticationType(106),
    UnknownDevice(70),
    UnknownFileSize(122),
    UnknownKey(107),
    UnknownKeyRevision(108),
    UnknownNetworkMessage(112),
    UnknownObject(31),
    UnknownProperty(32),
    UnknownRoute(71),
    UnknownSourceMessage(109),
    UnknownSubscription(79),
    UnknownVtClass(34),
    UnknownVtSession(35),
    UnsupportedObjectType(36),
    ValueNotInitialized(72),
    ValueOutOfRange(37),
    ValueTooLong(134),
    VtSessionAlreadyClosed(38),
    VtSessionTerminationFailure(39),
    WriteAccessDenied(40),
    WriteBdtFailed(116);

    private int ErrorCodeTypeInt;

    public static ErrorCodeType fromErrorCodeTypeInt(int ErrorCodeTypeInt) {
        for (ErrorCodeType type : values()) {
            if (type.getErrorCodeTypeInt() == ErrorCodeTypeInt) {
                return type;
            }
        }
        return null;
    }

    public static ErrorCodeType fromOctet(Octet errorCodeTypeOctet) throws NumberFormatException {
        if (errorCodeTypeOctet == null) {
            return null;
        }
        Integer ErrorCodeTypeInt = parseInt(errorCodeTypeOctet.toString(), 16);
        ErrorCodeType ErrorCodeType = fromErrorCodeTypeInt(ErrorCodeTypeInt.intValue());
        return ErrorCodeType;
    }


    public int getErrorCodeTypeInt() {
        return ErrorCodeTypeInt;
    }

    // enum constructor - cannot be public or protected
    private ErrorCodeType(int ErrorCodeTypeInt) {
        this.ErrorCodeTypeInt = ErrorCodeTypeInt;
    }
}
